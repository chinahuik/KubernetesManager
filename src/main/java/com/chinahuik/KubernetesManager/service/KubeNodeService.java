package com.chinahuik.KubernetesManager.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chinahuik.KubernetesManager.model.KubeCluster;
import com.chinahuik.KubernetesManager.model.KubeNode;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author open-source@chinahuik.com
 * @since 2020-06-08
 */
public interface KubeNodeService extends IService<KubeNode> {

	/**
	 * 增加或更新节点
	 *
	 * @param object
	 * @param kubeCluster
	 */
	void upsert(JSONObject object, KubeCluster kubeCluster);

	/**
	 * 增加或更新节点
	 *
	 * @param nodes
	 * @param kubeCluster
	 */
	void upserts(JSONArray nodes, KubeCluster kubeCluster);
}
