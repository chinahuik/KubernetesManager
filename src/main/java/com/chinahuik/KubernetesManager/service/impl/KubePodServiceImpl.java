package com.chinahuik.KubernetesManager.service.impl;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chinahuik.KubernetesManager.dao.KubePodDao;
import com.chinahuik.KubernetesManager.model.KubeCluster;
import com.chinahuik.KubernetesManager.model.KubePod;
import com.chinahuik.KubernetesManager.service.KubePodService;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author open-source@chinahuik.com
 * @since 2020-06-11
 */
@Service
@Slf4j
public class KubePodServiceImpl extends ServiceImpl<KubePodDao, KubePod>
		implements KubePodService {

	@Override
	public void upsert(JSONObject podObject, KubeCluster kubeCluster) {
		if (podObject == null || kubeCluster == null) {
			return;
		}
		try {
			final KubePod pod = KubePod.parseFromJson(podObject);
			if (pod == null) {
				return;
			}
			pod.setClusterId(kubeCluster.getId());
			final Wrapper<KubePod> entity = new QueryWrapper<KubePod>()
					.eq("name", pod.getName()).eq("namespace", pod.getNamespace())
					.eq("cluster_id", kubeCluster.getId());
			final int count = baseMapper.selectCount(entity);
			if (count < 1) {
				baseMapper.insert(pod);
			} else {
				final KubePod pod2 = baseMapper.selectOne(entity);
				pod.setId(pod2.getId());
				baseMapper.updateById(pod);
			}
		} catch (final Exception e) {
			log.error("upsert pod {} failed: {}", podObject.toJSONString(), e);
		}
	}

	@Override
	public void upserts(JSONArray pods, KubeCluster kubeCluster) {
		if (pods == null) {
			return;
		}
		for (int i = 0; i < pods.size(); i++) {
			final JSONObject podObject = pods.getJSONObject(i);
			upsert(podObject, kubeCluster);
		}
	}

}
