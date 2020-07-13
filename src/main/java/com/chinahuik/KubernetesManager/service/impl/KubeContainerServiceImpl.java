package com.chinahuik.KubernetesManager.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chinahuik.KubernetesManager.dao.KubeContainerDao;
import com.chinahuik.KubernetesManager.model.KubeCluster;
import com.chinahuik.KubernetesManager.model.KubeContainer;
import com.chinahuik.KubernetesManager.service.KubeContainerService;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author open-source@chinahuik.com
 * @since 2020-06-14
 */
@Service
@Slf4j
public class KubeContainerServiceImpl extends ServiceImpl<KubeContainerDao, KubeContainer>
		implements KubeContainerService {

	@Override
	public void upsert(JSONObject pod, KubeCluster kubeCluster) {
		if (pod == null) {
			return;
		}
		try {
			final JSONObject podMeta           = pod.getJSONObject("metadata");
			final String     podName           = podMeta.getString("name");
			final String     namespace         = podMeta.getString("namespace");
			final JSONObject podSpec           = pod.getJSONObject("spec");
			final JSONObject podStatus         = pod.getJSONObject("status");
			final JSONArray  containerInfos    = podSpec.getJSONArray("containers");
			final JSONArray  containerStatuses = podStatus.getJSONArray("containerStatuses");
			if (containerInfos != null) {
				final Map<String, KubeContainer> containerMap = parseContainersFromJson(containerInfos);
				updateContainerStatus(containerStatuses, containerMap);
				for (final Entry<String, KubeContainer> entry : containerMap.entrySet()) {
					final KubeContainer container = entry.getValue();
					upsert(kubeCluster, container, podName, namespace);
				}
			}
		} catch (final Exception e) {
			log.error("upsert containers {} failed: {}", pod.toJSONString(), e);
		}

	}

	@Override
	public void upserts(JSONArray pods, KubeCluster kubeCluster) {
		if (pods == null) {
			return;
		}
		for (int i = 0; i < pods.size(); i++) {
			final JSONObject pod = pods.getJSONObject(i);
			upsert(pod, kubeCluster);
		}

	}

	private Map<String, KubeContainer> parseContainersFromJson(final JSONArray containerInfos) {
		final Map<String, KubeContainer> containerMap = new HashMap<>();
		for (int i = 0; i < containerInfos.size(); i++) {
			final JSONObject    containerInfo = containerInfos.getJSONObject(i);
			final KubeContainer container     = KubeContainer.parseInfoFromJson(containerInfo);
			container.setContainerType("normal");
			containerMap.put(container.getName(), container);
		}
		return containerMap;
	}

	private void updateContainerStatus(final JSONArray containerStatuses,
			final Map<String, KubeContainer> containerMap) {
		if (containerStatuses != null) {
			for (int i = 0; i < containerStatuses.size(); i++) {
				final JSONObject    containerStatus = containerStatuses.getJSONObject(i);
				final KubeContainer container       = KubeContainer.parseStatusFromJson(containerStatus);
				if (containerMap.containsKey(container.getName())) {
					final KubeContainer kubeContainer = containerMap.get(container.getName());
					kubeContainer.updateStatus(container);
					containerMap.put(container.getName(), kubeContainer);
				}
			}
		}
	}

	private void upsert(KubeCluster kubeCluster, final KubeContainer container, final String podName,
			final String namespace) {
		container.setPodName(podName);
		container.setNamespace(namespace);
		container.setClusterId(kubeCluster.getId());
		final Wrapper<KubeContainer> entity = new QueryWrapper<KubeContainer>().eq("namespace", namespace)
				.eq("name", container.getName()).eq("pod_name", podName).eq("cluster_id", kubeCluster.getId());
		final int                    count  = baseMapper.selectCount(entity);
		if (count < 1) {
			baseMapper.insert(container);
		} else {
			final KubeContainer container2 = baseMapper.selectOne(entity);
			container.setId(container2.getId());
			container.setCreateTime(container2.getCreateTime());
			baseMapper.updateById(container);
		}
	}

}
