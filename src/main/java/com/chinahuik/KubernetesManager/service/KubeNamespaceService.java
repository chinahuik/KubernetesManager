package com.chinahuik.KubernetesManager.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chinahuik.KubernetesManager.model.KubeCluster;
import com.chinahuik.KubernetesManager.model.KubeNamespace;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author open-source@chinahuik.com
 * @since 2020-06-10
 */
public interface KubeNamespaceService extends IService<KubeNamespace> {
	/**
	 *
	 * @param namespace
	 * @param kubeCluster
	 */
	void upsert(JSONObject namespace, KubeCluster kubeCluster);

	/**
	 *
	 * @param namespaces
	 * @param kubeCluster
	 */
	void upserts(JSONArray namespaces, KubeCluster kubeCluster);

}
