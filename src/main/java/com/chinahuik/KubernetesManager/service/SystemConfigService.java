package com.chinahuik.KubernetesManager.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chinahuik.KubernetesManager.form.SystemConfigForm;
import com.chinahuik.KubernetesManager.model.SystemConfigModel;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author open-source@chinahuik.com
 * @since 2019-08-15
 */
public interface SystemConfigService extends IService<SystemConfigModel> {

	/**
	 * TODO
	 *
	 * @Author: open-source@chinahuik.com
	 * @Time: 2019年12月11日 上午12:01:00
	 * @param id
	 * @return
	 *
	 */
	List<SystemConfigModel> getRecords(Integer id);

	/**
	 * TODO
	 *
	 * @Author: open-source@chinahuik.com
	 * @Time: 2019年12月19日 下午12:40:22
	 * @param record
	 *
	 */
	void save(SystemConfigForm record);

	/**
	 * TODO
	 *
	 * @Author: open-source@chinahuik.com
	 * @Time: 2019年12月19日 下午12:53:01
	 * @param record
	 *
	 */
	void update(SystemConfigForm record);

}
