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
 * @since 2020-06-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "KubeDaemonset", description = "")
public class KubeDaemonset extends BaseModel {

	private String uid;

	private Integer clusterId;

	private String name;

	private String namespace;

	private String resourceVersion;

	private String annotations;

	private String selfLink;

	private String labels;

	private Integer revisionHistoryLimit;

	private String updateStrategy;

	private String selector;

	private String template;

	private Integer currentNumberScheduled;

	private Integer desiredNumberScheduled;

	private Integer numberMisscheduled;

	private Integer numberReady;

	private Integer numberUnavailable;

	private Integer observedGeneration;

	private Integer updatedNumberScheduled;

	public static KubeDaemonset parseFromJson(JSONObject itemObject) {
		final JSONObject metadata = itemObject.getJSONObject("metadata");
		final JSONObject spec = itemObject.getJSONObject("spec");
		final JSONObject status = itemObject.getJSONObject("status");
		KubeDaemonset item = new KubeDaemonset();
		item = parseMetadata(item, metadata);
		item = parseSpec(item, spec);
		item = parseStatus(item, status);
		return item;
	}

	private static KubeDaemonset parseMetadata(KubeDaemonset item, JSONObject metadata) {
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

	private static KubeDaemonset parseSpec(KubeDaemonset item, JSONObject spec) {
		item.setRevisionHistoryLimit(spec.getInteger("revisionHistoryLimit"));
		item.setSelector(spec.getString("selector"));
		item.setTemplate(spec.getString("template"));
		item.setUpdateStrategy(spec.getString("updateStrategy"));
		return item;
	}

	private static KubeDaemonset parseStatus(KubeDaemonset item, JSONObject status) {
		item.setCurrentNumberScheduled(status.getInteger("currentNumberScheduled"));
		item.setDesiredNumberScheduled(status.getInteger("desiredNumberScheduled"));
		item.setNumberMisscheduled(status.getInteger("numberMisscheduled"));
		item.setNumberReady(status.getInteger("numberReady"));
		item.setNumberUnavailable(status.getInteger("numberUnavailable"));
		item.setObservedGeneration(status.getInteger("observedGeneration"));
		item.setUpdatedNumberScheduled(status.getInteger("updatedNumberScheduled"));
		return item;
	}

}
