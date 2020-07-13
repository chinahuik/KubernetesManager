package com.chinahuik.KubernetesManager.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chinahuik.KubernetesManager.model.KubeNode;
import com.chinahuik.KubernetesManager.model.NodeImage;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author open-source@chinahuik.com
 * @since 2020-06-08
 */
public interface NodeImageService extends IService<NodeImage> {

	/**
	 *
	 * @param images
	 * @param node
	 */
	void upsert(JSONObject image, KubeNode node);

	/**
	 *
	 * @param images
	 * @param node
	 */
	void upserts(JSONArray images, KubeNode node);
}
