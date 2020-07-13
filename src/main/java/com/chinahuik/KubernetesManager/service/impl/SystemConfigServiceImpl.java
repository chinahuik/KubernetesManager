package com.chinahuik.KubernetesManager.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chinahuik.KubernetesManager.bean.UserPrincipal;
import com.chinahuik.KubernetesManager.dao.SystemConfigDao;
import com.chinahuik.KubernetesManager.form.SystemConfigForm;
import com.chinahuik.KubernetesManager.model.SystemConfigModel;
import com.chinahuik.KubernetesManager.service.ConfigService;
import com.chinahuik.KubernetesManager.service.SystemConfigService;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author open-source@chinahuik.com
 * @since 2019-08-15
 */
@Service
public class SystemConfigServiceImpl extends
		ServiceImpl<SystemConfigDao, SystemConfigModel> implements SystemConfigService {
	@Autowired
	private ConfigService configService;

	/*
	 * (non-Javadoc)
	 *
	 * @see com.chinahuik.config.service.SystemConfigService#getRecords(java.lang.
	 * String)
	 */
	@Override
	public List<SystemConfigModel> getRecords(Integer id) {
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put("group_id", id);
		final List<SystemConfigModel> models = baseMapper.selectByMap(params);
		return models;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.chinahuik.config.service.SystemConfigService#save(com.chinahuik.
	 * config.form.SystemConfigForm)
	 */
	@Override
	public void save(SystemConfigForm record) {
		final String userId = UserPrincipal.getUserName();
		final Wrapper<SystemConfigModel> entity = new QueryWrapper<SystemConfigModel>()
				.eq("config_name", record.getConfigName());
		final int total = baseMapper.selectCount(entity);
		if (total < 1) {
			final SystemConfigModel model = new SystemConfigModel(record, userId);
			baseMapper.insert(model);
		} else {
			final List<SystemConfigModel> list = baseMapper.selectList(entity);
			final SystemConfigModel configModel = list.get(0);
			configModel.setConfigValue(record.getConfigValue());
			baseMapper.updateById(configModel);
		}
		configService.setConfig(record.getConfigName(), record.getConfigValue());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.chinahuik.config.service.SystemConfigService#update(com.chinahuik.
	 * config.form.SystemConfigForm)
	 */
	@Override
	public void update(SystemConfigForm record) {
		final SystemConfigModel model = new SystemConfigModel();
		BeanUtils.copyProperties(record, model);
		final String userId = UserPrincipal.getUserName();
		model.setCreatedBy(userId);
		model.setUpdatedBy(userId);
		final Date now = new Date();
		model.setCreateTime(now);
		model.setUpdateTime(now);
		baseMapper.updateById(model);
		configService.setConfig(model.getConfigName(), model.getConfigValue());
	}

}
