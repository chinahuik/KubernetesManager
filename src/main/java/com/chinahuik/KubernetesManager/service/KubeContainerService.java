package com.chinahuik.KubernetesManager.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chinahuik.KubernetesManager.model.KubeCluster;
import com.chinahuik.KubernetesManager.model.KubeContainer;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author open-source@chinahuik.com
 * @since 2020-06-14
 */
public interface KubeContainerService extends IService<KubeContainer> {
	/**
	 *
	 * @param pod
	 * @param kubeCluster
	 */
	void upsert(JSONObject pod, KubeCluster kubeCluster);

	/***
	 *
	 * @param pods
	 * @param kubeCluster
	 */
	void upserts(JSONArray pods, KubeCluster kubeCluster);

}
