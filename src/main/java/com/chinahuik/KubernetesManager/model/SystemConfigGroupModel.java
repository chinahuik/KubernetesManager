package com.chinahuik.KubernetesManager.model;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import com.chinahuik.KubernetesManager.bean.BaseModel;
import com.chinahuik.KubernetesManager.form.SystemConfigGroupForm;

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
@TableName("system_config_group")
@ApiModel(value = "SystemConfigGroupModel", description = "")
public class SystemConfigGroupModel extends BaseModel {

	private String groupName;

	private String description;

	/**
	 *
	 * @Class: SystemConfigGroupModel
	 * @Time: 2019年12月17日 下午11:46:13
	 */
	public SystemConfigGroupModel() {

	}

	/**
	 * @Class: SystemConfigGroupModel
	 * @param form
	 * @param userId
	 * @Time: 2019年12月17日 下午11:45:38
	 */
	public SystemConfigGroupModel(SystemConfigGroupForm form, String userId) {
		groupName = form.getGroupName();
		description = form.getDescription();
		setCreatedBy(userId);
		setUpdatedBy(userId);
		final Date now = new Date();
		setCreateTime(now);
		setUpdateTime(now);
	}

}
