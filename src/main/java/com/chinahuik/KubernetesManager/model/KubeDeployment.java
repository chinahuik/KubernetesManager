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
 * @since 2020-06-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "KubeDeployment", description = "")
public class KubeDeployment extends BaseModel {

	private String uid;

	private Integer clusterId;

	private String name;

	private String namespace;

	private String resourceVersion;

	private String annotations;

	private String selfLink;

	private String labels;

	private Integer generation;

	private Integer replicas;

	private String selectLabels;

	private String strategyType;

	private Integer revisionHistoryLimit;

	private Integer progressDeadlineSeconds;

	private Integer observedGeneration;

	private Integer updatedReplicas;

	private Integer readyReplicas;

	private Integer availableReplicas;

	private String available;

	private String availableReason;

	private String availableMessage;

	private String progressing;

	private String progressingReason;

	private String progressingMessage;

	private String template;

	public static KubeDeployment parseFromJson(JSONObject deploy) {
		final JSONObject metadata   = deploy.getJSONObject("metadata");
		final JSONObject spec       = deploy.getJSONObject("spec");
		final JSONObject status     = deploy.getJSONObject("status");
		KubeDeployment   deployment = new KubeDeployment();
		deployment = parseMetadata(deployment, metadata);
		deployment = parseSpec(deployment, spec);
		deployment = parseStatus(deployment, status);
		return deployment;
	}

	private static KubeDeployment parseMetadata(KubeDeployment deployment, JSONObject metadata) {
		deployment.setCreatedBy("system");
		deployment.setUpdatedBy("system");
		deployment.setCreateTime(metadata.getDate("creationTimestamp"));
		deployment.setUpdateTime(new Date());
		deployment.setName(metadata.getString("name"));
		deployment.setNamespace(metadata.getString("namespace"));
		deployment.setSelfLink(metadata.getString("selfLink"));
		deployment.setUid(metadata.getString("uid"));
		deployment.setResourceVersion(metadata.getString("resourceVersion"));
		deployment.setAnnotations(metadata.getString("annotations"));
		deployment.setLabels(metadata.getString("labels"));
		deployment.setGeneration(metadata.getInteger("generation"));
		return deployment;
	}

	private static KubeDeployment parseSpec(KubeDeployment deployment, JSONObject spec) {
		final JSONObject selector = spec.getJSONObject("selector");
		final JSONObject strategy = spec.getJSONObject("strategy");
		deployment.setReplicas(spec.getInteger("replicas"));
		deployment.setTemplate(spec.getString("template"));
		deployment.setRevisionHistoryLimit(spec.getInteger("revisionHistoryLimit"));
		deployment.setProgressDeadlineSeconds(spec.getInteger("progressDeadlineSeconds"));
		deployment.setStrategyType(strategy.getString("type"));
		deployment.setSelectLabels(selector.getString("matchLabels"));
		return deployment;
	}

	private static KubeDeployment parseStatus(KubeDeployment deployment, JSONObject status) {
		final JSONArray conditions = status.getJSONArray("conditions");
		deployment.setObservedGeneration(status.getInteger("observedGeneration"));
		deployment.setUpdatedReplicas(status.getInteger("updatedReplicas"));
		deployment.setReadyReplicas(status.getInteger("readyReplicas"));
		deployment.setAvailableReplicas(status.getInteger("availableReplicas"));
		for (int i = 0; i < conditions.size(); i++) {
			final JSONObject condition = conditions.getJSONObject(i);
			final String     type      = condition.getString("type");
			if (type.equalsIgnoreCase("Available")) {
				deployment.setAvailable(condition.getString("status"));
				deployment.setAvailableMessage(condition.getString("message"));
				deployment.setAvailableReason(condition.getString("reason"));
			} else if (type.equalsIgnoreCase("Progressing")) {
				deployment.setProgressing(condition.getString("status"));
				deployment.setProgressingMessage(condition.getString("message"));
				deployment.setProgressingReason(condition.getString("reason"));
			}
		}
		return deployment;
	}

}
