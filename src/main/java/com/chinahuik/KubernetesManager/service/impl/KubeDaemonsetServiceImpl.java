package com.chinahuik.KubernetesManager.service.impl;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chinahuik.KubernetesManager.dao.KubeDaemonsetDao;
import com.chinahuik.KubernetesManager.model.KubeCluster;
import com.chinahuik.KubernetesManager.model.KubeDaemonset;
import com.chinahuik.KubernetesManager.service.KubeDaemonsetService;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author open-source@chinahuik.com
 * @since 2020-06-17
 */
@Service
@Slf4j
public class KubeDaemonsetServiceImpl extends ServiceImpl<KubeDaemonsetDao, KubeDaemonset>
		implements KubeDaemonsetService {

	@Override
	public void upsert(JSONObject itemObject, KubeCluster kubeCluster) {
		if (itemObject == null) {
			return;
		}
		try {
			final KubeDaemonset item = KubeDaemonset.parseFromJson(itemObject);
			if (item == null) {
				return;
			}
			item.setClusterId(kubeCluster.getId());
			final Wrapper<KubeDaemonset> entity = new QueryWrapper<KubeDaemonset>()
					.eq("name", item.getName()).eq("namespace", item.getNamespace())
					.eq("cluster_id", kubeCluster.getId());
			final int count = baseMapper.selectCount(entity);
			if (count < 1) {
				baseMapper.insert(item);
			} else {
				final KubeDaemonset item2 = baseMapper.selectOne(entity);
				item.setId(item2.getId());
				baseMapper.updateById(item);
			}
		} catch (final Exception e) {
			log.error("upsert service {} failed: {}", itemObject.toJSONString(), e);
		}

	}

	@Override
	public void upserts(JSONArray items, KubeCluster kubeCluster) {
		if (items == null) {
			return;
		}
		for (int i = 0; i < items.size(); i++) {
			final JSONObject item = items.getJSONObject(i);
			upsert(item, kubeCluster);
		}

	}

}
