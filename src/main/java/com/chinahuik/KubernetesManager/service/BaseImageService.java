package com.chinahuik.KubernetesManager.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chinahuik.KubernetesManager.model.BaseImage;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author open-source@chinahuik.com
 * @since 2020-06-08
 */
public interface BaseImageService extends IService<BaseImage> {

	/**
	 *
	 * @param image
	 */
	void upsert(JSONObject image);

	/**
	 *
	 * @param images
	 */
	void upserts(JSONArray images);

}
