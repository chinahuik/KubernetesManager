package com.chinahuik.KubernetesManager.service.impl;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chinahuik.KubernetesManager.dao.KubeDeploymentDao;
import com.chinahuik.KubernetesManager.model.KubeCluster;
import com.chinahuik.KubernetesManager.model.KubeDeployment;
import com.chinahuik.KubernetesManager.service.KubeDeploymentService;

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
public class KubeDeploymentServiceImpl extends ServiceImpl<KubeDeploymentDao, KubeDeployment>
		implements KubeDeploymentService {

	@Override
	public void upsert(JSONObject deploy, KubeCluster kubeCluster) {
		if (deploy == null || kubeCluster == null) {
			return;
		}
		try {
			final KubeDeployment deployment = KubeDeployment.parseFromJson(deploy);
			if (deployment == null) {
				return;
			}
			deployment.setClusterId(kubeCluster.getId());
			final Wrapper<KubeDeployment> entity = new QueryWrapper<KubeDeployment>()
					.eq("namespace", deployment.getNamespace()).eq("name", deployment.getName())
					.eq("cluster_id", kubeCluster.getId());
			final int                     count  = baseMapper.selectCount(entity);
			if (count < 1) {
				baseMapper.insert(deployment);
			} else {
				final KubeDeployment deployment2 = baseMapper.selectOne(entity);
				deployment.setId(deployment2.getId());
				baseMapper.updateById(deployment);
			}
		} catch (final Exception e) {
			log.error("upsert deployment {} failed: {}", deploy.toJSONString(), e);
		}
	}

	@Override
	public void upserts(JSONArray deps, KubeCluster kubeCluster) {
		if (deps == null) {
			return;
		}
		for (int i = 0; i < deps.size(); i++) {
			final JSONObject deploy = deps.getJSONObject(i);
			upsert(deploy, kubeCluster);
		}

	}

}
