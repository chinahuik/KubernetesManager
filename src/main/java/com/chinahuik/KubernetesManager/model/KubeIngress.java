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
@ApiModel(value = "KubeIngress", description = "")
public class KubeIngress extends BaseModel {

	private String uid;

	private Integer clusterId;

	private String name;

	private String namespace;

	private String resourceVersion;

	private String annotations;

	private String selfLink;

	private String labels;

	private String rules;

	private String tls;

	/**
	 *
	 * @param itemObject
	 * @return
	 */
	public static KubeIngress parseFromJson(JSONObject itemObject) {
		final JSONObject metadata = itemObject.getJSONObject("metadata");
		final JSONObject spec     = itemObject.getJSONObject("spec");
		KubeIngress      item     = new KubeIngress();
		item = parseMetadata(item, metadata);
		item = parseSpec(item, spec);
		return item;
	}

	private static KubeIngress parseMetadata(KubeIngress item, JSONObject metadata) {
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
		return item;
	}

	private static KubeIngress parseSpec(KubeIngress item, JSONObject spec) {
		item.setTls(spec.getString("tls"));
		item.setRules(spec.getString("rules"));
		return item;
	}

}
