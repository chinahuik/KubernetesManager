package com.chinahuik.KubernetesManager.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chinahuik.KubernetesManager.model.KubeCluster;
import com.chinahuik.KubernetesManager.model.KubeDaemonset;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author open-source@chinahuik.com
 * @since 2020-06-17
 */
public interface KubeDaemonsetService extends IService<KubeDaemonset> {
	/**
	 *
	 * @param itemObject
	 * @param kubeCluster
	 */
	void upsert(JSONObject itemObject, KubeCluster kubeCluster);

	/**
	 * 
	 * @param items
	 * @param kubeCluster
	 */
	void upserts(JSONArray items, KubeCluster kubeCluster);

}
