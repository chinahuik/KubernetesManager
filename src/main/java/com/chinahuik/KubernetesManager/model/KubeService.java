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
 * @since 2020-06-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "KubeService", description = "")
public class KubeService extends BaseModel {

	private String uid;

	private Integer clusterId;

	private String name;

	private String namespace;

	private String resourceVersion;

	private String annotations;

	private String selfLink;

	private String labels;

	private String ports;

	private String selector;

	private String clusterIp;

	private String serviceType;

	private String sessionAffinity;

	public static KubeService parseFromJson(JSONObject itemObject) {
		final JSONObject metadata = itemObject.getJSONObject("metadata");
		final JSONObject spec = itemObject.getJSONObject("spec");
		KubeService item = new KubeService();
		item = parseMetadata(item, metadata);
		item = parseSpec(item, spec);
		return item;
	}

	private static KubeService parseMetadata(KubeService item, JSONObject metadata) {
		item.setCreatedBy("system");
		item.setUpdatedBy("system");
		item.setCreateTime(metadata.getDate("creationTimestamp"));
		item.setUpdateTime(new Date());
		item.setName(metadata.getString("name"));
		item.setNamespace(metadata.getString("namespace"));
		item.setSelfLink(metadata.getString("selfLink"));
		item.setUid(metadata.getString("uid"));
		item.setResourceVersion(metadata.getString("resourceVersion"));
		item.setAnnotations(metadata.getString("annotations"));
		item.setLabels(metadata.getString("labels"));
		// pod.setGenerateName(metadata.getString("generateName"));
		return item;

	}

	private static KubeService parseSpec(KubeService item, JSONObject spec) {
		item.setPorts(spec.getString("ports"));
		item.setSelector(spec.getString("selector"));
		item.setClusterIp(spec.getString("clusterIP"));
		item.setServiceType(spec.getString("type"));
		item.setSessionAffinity(spec.getString("sessionAffinity"));
		return item;
	}

}
