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
@ApiModel(value = "KubePod", description = "")
public class KubePod extends BaseModel {

	private String uid;

	private Integer clusterId;

	private String name;

	private String namespace;

	private String resourceVersion;

	private String ownerReferences;

	private String selfLink;

	private String labels;

	private String restartPolicy;

	private Integer terminationGracePeriodSeconds;

	private String dnsPolicy;

	private String serviceAccountName;

	private String serviceAccount;

	private String nodeName;

	private String schedulerName;

	private String securityContext;

	private String tolerations;

	private Boolean enableServiceLinks;

	private String phase;

	private String hostIp;

	private String podIp;

	private String qosClass;

	private String initialized;

	private Date initializedUpdateTime;

	private String podReady;

	private Date podReadyUpdateTime;

	private String containersReady;

	private Date containersReadyUpdateTime;

	private String podScheduled;

	private Date podScheduledUpdateTime;
	private Date startTime;

	public static KubePod parseFromJson(JSONObject podObject) {
		final JSONObject metadata = podObject.getJSONObject("metadata");
		final JSONObject spec     = podObject.getJSONObject("spec");
		final JSONObject status   = podObject.getJSONObject("status");
		KubePod          pod      = new KubePod();
		pod = parseMetadata(pod, metadata);
		pod = parseSpec(pod, spec);
		pod = parseStatus(pod, status);
		return pod;
	}

	private static KubePod parseMetadata(KubePod pod, JSONObject metadata) {
		pod.setCreatedBy("system");
		pod.setUpdatedBy("system");
		pod.setCreateTime(metadata.getDate("creationTimestamp"));
		pod.setUpdateTime(new Date());
		pod.setName(metadata.getString("name"));
		pod.setNamespace(metadata.getString("namespace"));
		pod.setSelfLink(metadata.getString("selfLink"));
		pod.setUid(metadata.getString("uid"));
		pod.setResourceVersion(metadata.getString("resourceVersion"));
		pod.setOwnerReferences(metadata.getString("ownerReferences"));
		pod.setLabels(metadata.getString("labels"));
		// pod.setGenerateName(metadata.getString("generateName"));
		return pod;
	}

	private static KubePod parseSpec(KubePod pod, JSONObject spec) {
		pod.setRestartPolicy(spec.getString("restartPolicy"));
		pod.setTerminationGracePeriodSeconds(spec.getInteger("terminationGracePeriodSeconds"));
		pod.setDnsPolicy(spec.getString("dnsPolicy"));
		pod.setServiceAccountName(spec.getString("serviceAccountName"));
		pod.setServiceAccount(spec.getString("serviceAccount"));
		pod.setNodeName(spec.getString("nodeName"));
		pod.setSecurityContext(spec.getString("securityContext"));
		pod.setSchedulerName(spec.getString("schedulerName"));
		pod.setTolerations(spec.getString("tolerations"));
		pod.setEnableServiceLinks(spec.getBoolean("enableServiceLinks"));
		// pod.setPriority(spec.getInteger("priority"));
		return pod;
	}

	private static KubePod parseStatus(KubePod pod, JSONObject status) {
		final JSONArray conditions = status.getJSONArray("conditions");
		pod.setPhase(status.getString("phase"));
		pod.setStartTime(status.getDate("startTime"));
		pod.setHostIp(status.getString("hostIP"));
		pod.setPodIp(status.getString("podIP"));
		pod.setQosClass(status.getString("qosClass"));
		for (int i = 0; i < conditions.size(); i++) {
			final JSONObject condition = conditions.getJSONObject(i);
			final String     type      = condition.getString("type");
			if (type.equalsIgnoreCase("Initialized")) {
				pod.setInitialized(condition.getString("status"));
				pod.setInitializedUpdateTime(condition.getDate("lastTransitionTime"));
			} else if (type.equalsIgnoreCase("ContainersReady")) {
				pod.setContainersReady(condition.getString("status"));
				pod.setContainersReadyUpdateTime(condition.getDate("lastTransitionTime"));
			} else if (type.equalsIgnoreCase("Ready")) {
				pod.setPodReady(condition.getString("status"));
				pod.setPodReadyUpdateTime(condition.getDate("lastTransitionTime"));
			} else if (type.equalsIgnoreCase("PodScheduled")) {
				pod.setPodScheduled(condition.getString("status"));
				pod.setPodScheduledUpdateTime(condition.getDate("lastTransitionTime"));
			}
		}
		return pod;
	}

}
