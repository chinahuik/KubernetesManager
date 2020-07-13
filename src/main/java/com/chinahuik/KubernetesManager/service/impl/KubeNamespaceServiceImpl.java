package com.chinahuik.KubernetesManager.service.impl;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chinahuik.KubernetesManager.dao.KubeNamespaceDao;
import com.chinahuik.KubernetesManager.model.KubeCluster;
import com.chinahuik.KubernetesManager.model.KubeNamespace;
import com.chinahuik.KubernetesManager.service.KubeNamespaceService;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author open-source@chinahuik.com
 * @since 2020-06-10
 */
@Service
@Slf4j
public class KubeNamespaceServiceImpl extends ServiceImpl<KubeNamespaceDao, KubeNamespace>
		implements KubeNamespaceService {

	@Override
	public void upsert(JSONObject namespace, KubeCluster kubeCluster) {
		if (namespace == null) {
			return;
		}
		try {
			final KubeNamespace kubeNamespace = KubeNamespace.parseFromJson(namespace);
			if (kubeNamespace == null) {
				return;
			}
			kubeNamespace.setClusterId(kubeCluster.getId());
			final Wrapper<KubeNamespace> entity = new QueryWrapper<KubeNamespace>()
					.eq("namespace", kubeNamespace.getNamespace()).eq("cluster_id", kubeCluster.getId());
			final int                    count  = baseMapper.selectCount(entity);
			if (count < 1) {
				baseMapper.insert(kubeNamespace);
			} else {
				final KubeNamespace kubeNamespace2 = baseMapper.selectOne(entity);
				kubeNamespace.setId(kubeNamespace2.getId());
				baseMapper.updateById(kubeNamespace);
			}
		} catch (final Exception e) {
			log.error("upsert namespace {} failed: {}", namespace.toJSONString(), e);
		}
	}

	@Override
	public void upserts(JSONArray namespaces, KubeCluster kubeCluster) {
		if (namespaces == null) {
			return;
		}
		for (int i = 0; i < namespaces.size(); i++) {
			final JSONObject namespace = namespaces.getJSONObject(i);
			upsert(namespace, kubeCluster);
		}
	}

}
