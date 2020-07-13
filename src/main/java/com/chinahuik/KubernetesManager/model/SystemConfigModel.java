package com.chinahuik.KubernetesManager.model;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import com.chinahuik.KubernetesManager.bean.BaseModel;
import com.chinahuik.KubernetesManager.form.SystemConfigForm;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author open-source@chinahuik.com
 * @since 2019-08-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("system_config")
@ApiModel(value = "SystemConfigModel", description = "")
public class SystemConfigModel extends BaseModel {

	private Integer groupId;

	private String configName;

	private String configValue;

	private String description;

	/**
	 *
	 * @Class: SystemConfigModel
	 * @Time: 2019年12月19日 下午12:42:27
	 */
	public SystemConfigModel() {

	}

	/**
	 * @Class: SystemConfigModel
	 * @param form
	 * @param userId
	 * @Time: 2019年12月19日 下午12:41:57
	 */
	public SystemConfigModel(SystemConfigForm form, String userId) {
		groupId = form.getGroupId();
		description = form.getDescription();
		configName = form.getConfigName();
		configValue = form.getConfigValue();
		setCreatedBy(userId);
		setUpdatedBy(userId);
		final Date now = new Date();
		setCreateTime(now);
		setUpdateTime(now);
	}

}
