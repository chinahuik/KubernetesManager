package com.chinahuik.KubernetesManager.dao;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chinahuik.KubernetesManager.dto.KubeNodeStatDto;
import com.chinahuik.KubernetesManager.model.KubeNode;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author open-source@chinahuik.com
 * @since 2020-06-08
 */
public interface KubeNodeDao extends BaseMapper<KubeNode> {
	List<KubeNodeStatDto> statResourceUse(Map<String, Object> params);
}
