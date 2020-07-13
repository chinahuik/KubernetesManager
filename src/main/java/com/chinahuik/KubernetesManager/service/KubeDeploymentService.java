package com.chinahuik.KubernetesManager.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chinahuik.KubernetesManager.model.KubeCluster;
import com.chinahuik.KubernetesManager.model.KubeDeployment;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author open-source@chinahuik.com
 * @since 2020-06-11
 */
public interface KubeDeploymentService extends IService<KubeDeployment> {
	/**
	 *
	 * @param deploy
	 * @param kubeCluster
	 */
	void upsert(JSONObject deploy, KubeCluster kubeCluster);

	/**
	 *
	 * @param deps
	 * @param kubeCluster
	 */
	void upserts(JSONArray deps, KubeCluster kubeCluster);

}
