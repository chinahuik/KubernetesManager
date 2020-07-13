package com.chinahuik.KubernetesManager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chinahuik.KubernetesManager.dao.BaseImageDao;
import com.chinahuik.KubernetesManager.dao.NodeImageDao;
import com.chinahuik.KubernetesManager.model.BaseImage;
import com.chinahuik.KubernetesManager.model.KubeNode;
import com.chinahuik.KubernetesManager.model.NodeImage;
import com.chinahuik.KubernetesManager.service.NodeImageService;

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
public class NodeImageServiceImpl extends ServiceImpl<NodeImageDao, NodeImage>
		implements NodeImageService {

	@Autowired
	private BaseImageDao imageDao;

	@Override
	public void upsert(JSONObject image, KubeNode node) {
		if (image == null || node == null) {
			return;
		}
		try {
			BaseImage baseImage = BaseImage.parseFromJson(image);
			final Wrapper<BaseImage> biWrapper = new QueryWrapper<BaseImage>()
					.eq("image_host", baseImage.getImageHost())
					.eq("image_repo", baseImage.getImageRepo())
					.eq("image_tag", baseImage.getImageTag());
			baseImage = imageDao.selectOne(biWrapper);
			final NodeImage nodeImage = new NodeImage(node, baseImage);
			final Wrapper<NodeImage> niWrapper = new QueryWrapper<NodeImage>()
					.eq("image_id", baseImage.getId()).eq("node_id", node.getId());
			final int count = baseMapper.selectCount(niWrapper);
			if (count < 1) {
				baseMapper.insert(nodeImage);
			}

		} catch (final Exception e) {
			log.error("upsert node image {} {} failed: {}", node, image, e);
		}

	}

	@Override
	public void upserts(JSONArray images, KubeNode node) {
		if (images == null) {
			return;
		}
		for (int i = 0; i < images.size(); i++) {
			final JSONObject image = images.getJSONObject(i);
			upsert(image, node);
		}

	}

}
