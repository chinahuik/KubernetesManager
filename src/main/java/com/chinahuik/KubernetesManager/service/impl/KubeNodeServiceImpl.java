package com.chinahuik.KubernetesManager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chinahuik.KubernetesManager.dao.KubeNodeDao;
import com.chinahuik.KubernetesManager.model.KubeCluster;
import com.chinahuik.KubernetesManager.model.KubeNode;
import com.chinahuik.KubernetesManager.service.BaseImageService;
import com.chinahuik.KubernetesManager.service.KubeNodeService;
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
public class KubeNodeServiceImpl extends ServiceImpl<KubeNodeDao, KubeNode>
		implements KubeNodeService {

	@Autowired
	private BaseImageService imageService;
	@Autowired
	private NodeImageService nodeImageService;

	@Override
	public void upsert(JSONObject object, KubeCluster kubeCluster) {
		if (object == null) {
			return;
		}
		try {
			KubeNode node = KubeNode.parseFromJson(object);
			node.setClusterId(kubeCluster.getId());
			final JSONArray images = object.getJSONObject("status")
					.getJSONArray("images");

			final Wrapper<KubeNode> entity = new QueryWrapper<KubeNode>()
					.eq("host_name", node.getHostName())
					.eq("internal_ip", node.getInternalIp());
			final int count = baseMapper.selectCount(entity);
			if (count > 0) {
				final KubeNode mNode = baseMapper.selectOne(entity);
				node.setId(mNode.getId());
				baseMapper.updateById(node);
			} else {
				baseMapper.insert(node);
			}
			node = baseMapper.selectOne(entity);
			imageService.upserts(images);
			nodeImageService.upserts(images, node);
		} catch (final Exception e) {
			log.error("upsert node {} failed: {}", object.toJSONString(), e);
		}
	}

	@Override
	public void upserts(JSONArray nodes, KubeCluster kubeCluster) {
		for (int i = 0; i < nodes.size(); i++) {
			upsert(nodes.getJSONObject(i), kubeCluster);
		}
	}

}
