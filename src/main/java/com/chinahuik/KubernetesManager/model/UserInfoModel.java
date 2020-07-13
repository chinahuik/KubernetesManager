package com.chinahuik.KubernetesManager.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.chinahuik.KubernetesManager.bean.BaseModel;

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
@TableName("user_info")
@ApiModel(value = "UserInfoModel", description = "")
public class UserInfoModel extends BaseModel {

	private Integer groupId;

	private String username;

	private String passMd5;

	private String roles;

	private String description;

	private String avatar;
}
