/**
 *
 */
package com.chinahuik.KubernetesManager.job;

import java.io.StringReader;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chinahuik.KubernetesManager.dao.KubeClusterDao;
import com.chinahuik.KubernetesManager.dao.KubeNamespaceDao;
import com.chinahuik.KubernetesManager.model.KubeCluster;
import com.chinahuik.KubernetesManager.model.KubeNamespace;
import com.chinahuik.KubernetesManager.service.KubeJobService;
import com.chinahuik.utils.KubeApiClient;
import com.chinahuik.utils.KubeApiConfig;

import lombok.extern.slf4j.Slf4j;

/**
 * @author open-source@chinahuik.com
 *
 */
@Component
@EnableScheduling // 1.开启定时任务
@EnableAsync // 2.开启多线程
@Slf4j
public class KubeSyncJobThread {
	@Autowired
	private KubeClusterDao kubeClusterDao;

	@Autowired
	private KubeNamespaceDao namespaceDao;

	@Autowired
	private KubeJobService service;

	@Value("${job.sync.job.enabed}")
	private boolean cronEnabled;

	@Async("taskExecutor")
	@Scheduled(cron = "${job.sync.job.cron}")
	// @Scheduled(fixedDelay = 1000) // 间隔1秒
	public void execute() {
		if (!cronEnabled) {
			log.info("kube sync deploy job not enabled.");
			return;
		}
		final List<KubeCluster> clusters = kubeClusterDao.selectList(null);
		if (clusters != null) {
			for (final KubeCluster kubeCluster : clusters) {
				try (StringReader reader = new StringReader(kubeCluster.getConfig());) {
					final Wrapper<KubeNamespace> entity = new QueryWrapper<KubeNamespace>()
							.eq("cluster_id", kubeCluster.getId());
					final List<KubeNamespace> namespaces = namespaceDao
							.selectList(entity);
					if (namespaces == null) {
						continue;
					}
					final KubeApiConfig config = KubeApiConfig.loadKubeConfig(reader);
					final KubeApiClient client = new KubeApiClient(config);
					for (final KubeNamespace namespace : namespaces) {
						// final String api = String.format(
						// "/apis/extensions/v1beta1/namespaces/%s/deployments",
						// namespace.getNamespace().trim());
						final String api = String.format(
								"/apis/batch/v1/namespaces/%s/jobs",
								namespace.getNamespace().trim());
						final String resp = client.doGet(api);
						log.info("response: {}", resp);
						if (resp != null && !resp.trim().isEmpty()) {
							final JSONObject object = JSONObject.parseObject(resp);
							if (object.getString("kind").equalsIgnoreCase("JobList")) {
								final JSONArray items = object.getJSONArray("items");
								service.upserts(items, kubeCluster);
							}
							log.info("object: {}", object.toString());
						}
					}
				} catch (final Exception e) {
					log.error("get info failed from {}: {}", kubeCluster, e);
				}
			}
		}
		log.info("kube sync job finished.");
	}
}
