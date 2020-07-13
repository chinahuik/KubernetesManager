package com.chinahuik.KubernetesManager.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chinahuik.KubernetesManager.bean.UserPrincipal;
import com.chinahuik.KubernetesManager.dao.SystemConfigGroupDao;
import com.chinahuik.KubernetesManager.form.SystemConfigGroupForm;
import com.chinahuik.KubernetesManager.model.SystemConfigGroupModel;
import com.chinahuik.KubernetesManager.service.SystemConfigGroupService;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author open-source@chinahuik.com
 * @since 2019-08-15
 */
@Service
public class SystemConfigGroupServiceImpl
		extends ServiceImpl<SystemConfigGroupDao, SystemConfigGroupModel>
		implements SystemConfigGroupService {

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.chinahuik.config.service.SystemConfigGroupService#save(com.chinahuik.
	 * config.form.SystemConfigGroupForm)
	 */
	@Override
	public void save(SystemConfigGroupForm form) {
		final String userId = UserPrincipal.getUserName();
		final SystemConfigGroupModel model = new SystemConfigGroupModel(form, userId);
		baseMapper.insert(model);
	}

}
