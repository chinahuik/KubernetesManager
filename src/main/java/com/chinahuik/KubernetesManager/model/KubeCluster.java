package com.chinahuik.KubernetesManager.model;

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
 * @since 2020-06-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "KubeCluster", description = "")
public class KubeCluster extends BaseModel {

	private String name;

	private String nameCn;

	private String serverHost;

	private String tillerHost;

	private Integer serverPort;

	private Integer tillerPort;

	private String config;

}
