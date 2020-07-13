package com.chinahuik.KubernetesManager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chinahuik.KubernetesManager.form.SystemConfigGroupForm;
import com.chinahuik.KubernetesManager.model.SystemConfigGroupModel;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author open-source@chinahuik.com
 * @since 2019-08-15
 */
public interface SystemConfigGroupService extends IService<SystemConfigGroupModel> {

	/**
	 * TODO
	 *
	 * @Author: open-source@chinahuik.com
	 * @Time: 2019年12月17日 下午11:41:37
	 * @param form
	 *
	 */
	void save(SystemConfigGroupForm form);

}
