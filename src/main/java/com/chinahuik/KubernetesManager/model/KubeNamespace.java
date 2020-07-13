package com.chinahuik.KubernetesManager.model;

import java.util.Date;

import com.alibaba.fastjson.JSONObject;
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
 * @since 2020-06-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "KubeNamespace", description = "")
public class KubeNamespace extends BaseModel {

	private Integer clusterId;

	private String namespace;

	private String selfLink;

	private String uid;

	private String resourceVersion;

	private String statusPhase;

	public static KubeNamespace parseFromJson(JSONObject nsObject) {
		final JSONObject metadata = nsObject.getJSONObject("metadata");
		final JSONObject status = nsObject.getJSONObject("status");
		final KubeNamespace kubeNamespace = new KubeNamespace();
		kubeNamespace.setCreatedBy("system");
		kubeNamespace.setUpdatedBy("system");
		kubeNamespace.setCreateTime(metadata.getDate("creationTimestamp"));
		kubeNamespace.setUpdateTime(new Date());
		kubeNamespace.setNamespace(metadata.getString("name"));
		kubeNamespace.setSelfLink(metadata.getString("selfLink"));
		kubeNamespace.setUid(metadata.getString("uid"));
		kubeNamespace.setResourceVersion(metadata.getString("resourceVersion"));
		kubeNamespace.setStatusPhase(status.getString("phase"));
		return kubeNamespace;
	}

}
