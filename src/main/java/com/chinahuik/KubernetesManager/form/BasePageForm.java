package com.chinahuik.KubernetesManager.form;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "返回对象", description = "分页对象")
public class BasePageForm {
	/**
	 * 当前页面
	 */
	private Long currentPage;
	/**
	 * 页面大小
	 */

	private Long pageSize;
	/**
	 * 集群编号
	 */
	private Integer clusterId;
	/**
	 * 命名空间
	 */
	private String namespace;
	/**
	 * 集群名称
	 */
	private String clusterName;
	/**
	 * 状态
	 */
	private String status;
}
