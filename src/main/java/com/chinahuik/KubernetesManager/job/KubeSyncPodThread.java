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
import com.chinahuik.KubernetesManager.dao.KubeClusterDao;
import com.chinahuik.KubernetesManager.model.KubeCluster;
import com.chinahuik.KubernetesManager.service.KubeContainerService;
import com.chinahuik.KubernetesManager.service.KubePodService;
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
public class KubeSyncPodThread {
	@Autowired
	private KubeClusterDao kubeClusterDao;

	@Autowired
	private KubePodService podService;
	@Autowired
	private KubeContainerService containerService;

	@Value("${job.sync.pod.enabed}")
	private boolean cronEnabled;

	@Async("taskExecutor")
	@Scheduled(cron = "${job.sync.pod.cron}")
	// @Scheduled(fixedDelay = 1000) // 间隔1秒
	public void execute() {
		if (!cronEnabled) {
			log.info("kube sync pod job not enabled.");
			return;
		}
		final List<KubeCluster> clusters = kubeClusterDao.selectList(null);
		if (clusters != null) {
			for (final KubeCluster kubeCluster : clusters) {
				try (StringReader reader = new StringReader(kubeCluster.getConfig());) {
					final KubeApiConfig config = KubeApiConfig.loadKubeConfig(reader);
					final KubeApiClient client = new KubeApiClient(config);
					final String resp = client.doGet("/api/v1/pods");
					log.info("response: {}", resp);
					if (resp != null && !resp.trim().isEmpty()) {
						final JSONObject object = JSONObject.parseObject(resp);
						if (object.getString("kind").equalsIgnoreCase("PodList")) {
							final JSONArray pods = object.getJSONArray("items");
							podService.upserts(pods, kubeCluster);
							containerService.upserts(pods, kubeCluster);
						}
						log.info("object: {}", object.toString());
					}
				} catch (final Exception e) {
					log.error("get info failed from {}: {}", kubeCluster, e);
				}
			}
		}
		log.info("-----kube sync pod job finished.----");
	}
}
