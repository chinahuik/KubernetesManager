package com.chinahuik.KubernetesManager.model;

import java.util.Date;

import com.alibaba.fastjson.JSONArray;
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
@ApiModel(value = "KubeJob", description = "")
public class KubeJob extends BaseModel {

	private String uid;

	private Integer clusterId;

	private String name;

	private String namespace;

	private String resourceVersion;

	private String annotations;

	private String selfLink;

	private String labels;

	private Integer backoffLimit;

	private Integer completions;

	private Integer parallelism;

	private String selector;

	private String template;

	private Date completionTime;

	private Date startTime;

	private Integer succeeded;

	private String completeStatus;

	private Date completeTime;

	public static KubeJob parseFromJson(JSONObject itemObject) {
		final JSONObject metadata = itemObject.getJSONObject("metadata");
		final JSONObject spec = itemObject.getJSONObject("spec");
		final JSONObject status = itemObject.getJSONObject("status");
		KubeJob item = new KubeJob();
		item = parseMetadata(item, metadata);
		item = parseSpec(item, spec);
		item = parseStatus(item, status);
		return item;
	}

	private static KubeJob parseMetadata(KubeJob item, JSONObject metadata) {
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

	private static KubeJob parseSpec(KubeJob item, JSONObject spec) {
		item.setBackoffLimit(spec.getInteger("backoffLimit"));
		item.setCompletions(spec.getInteger("completions"));
		item.setParallelism(spec.getInteger("parallelism"));
		item.setSelector(spec.getString("selector"));
		item.setTemplate(spec.getString("template"));
		return item;
	}

	private static KubeJob parseStatus(KubeJob item, JSONObject status) {
		item.setSucceeded(status.getInteger("succeeded"));
		item.setStartTime(status.getDate("startTime"));
		item.setCompletionTime(status.getDate("completionTime"));
		final JSONArray conditions = status.getJSONArray("conditions");
		for (int i = 0; i < conditions.size(); i++) {
			final JSONObject condition = conditions.getJSONObject(i);
			final String type = condition.getString("type");
			if (type.equalsIgnoreCase("Complete")) {
				item.setCompleteStatus(condition.getString("status"));
				item.setCompleteTime(condition.getDate("lastTransitionTime"));
			}
		}
		return item;
	}

}
