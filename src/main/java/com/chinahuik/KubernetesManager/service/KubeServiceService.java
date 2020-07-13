package com.chinahuik.KubernetesManager.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chinahuik.KubernetesManager.model.KubeCluster;
import com.chinahuik.KubernetesManager.model.KubeService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author open-source@chinahuik.com
 * @since 2020-06-16
 */
public interface KubeServiceService extends IService<KubeService> {
	/**
	 *
	 * @param item
	 * @param kubeCluster
	 */
	void upsert(JSONObject item, KubeCluster kubeCluster);

	/**
	 * 
	 * @param items
	 * @param kubeCluster
	 */
	void upserts(JSONArray items, KubeCluster kubeCluster);

}
