package com.chinahuik.KubernetesManager.service.impl;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chinahuik.KubernetesManager.dao.BaseImageDao;
import com.chinahuik.KubernetesManager.model.BaseImage;
import com.chinahuik.KubernetesManager.service.BaseImageService;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author open-source@chinahuik.com
 * @since 2020-06-08
 */
@Service
@Slf4j
public class BaseImageServiceImpl extends ServiceImpl<BaseImageDao, BaseImage>
		implements BaseImageService {

	@Override
	public void upsert(JSONObject image) {
		if (image == null) {
			return;
		}
		try {
			final BaseImage baseImage = BaseImage.parseFromJson(image);
			if (baseImage == null) {
				return;
			}
			final Wrapper<BaseImage> entity = new QueryWrapper<BaseImage>()
					.eq("image_host", baseImage.getImageHost())
					.eq("image_repo", baseImage.getImageRepo())
					.eq("image_tag", baseImage.getImageTag());
			final int count = baseMapper.selectCount(entity);
			if (count < 1) {
				baseMapper.insert(baseImage);
			}
		} catch (final Exception e) {
			log.error("upsert image {} failed: {}", image.toJSONString(), e);
		}
	}

	@Override
	public void upserts(JSONArray images) {
		if (images != null) {
			for (int i = 0; i < images.size(); i++) {
				final JSONObject image = images.getJSONObject(i);
				upsert(image);
			}
		}
	}

}
