package com.chinahuik.KubernetesManager.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chinahuik.KubernetesManager.model.KubeCluster;
import com.chinahuik.KubernetesManager.model.KubePod;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author open-source@chinahuik.com
 * @since 2020-06-11
 */
public interface KubePodService extends IService<KubePod> {

	/**
	 *
	 * @param podObject
	 * @param kubeCluster
	 */
	void upsert(JSONObject podObject, KubeCluster kubeCluster);

	/**
	 * 
	 * @param pods
	 * @param kubeCluster
	 */
	void upserts(JSONArray pods, KubeCluster kubeCluster);

}
