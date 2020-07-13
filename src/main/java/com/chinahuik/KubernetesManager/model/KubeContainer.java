package com.chinahuik.KubernetesManager.model;

import java.util.Date;

import com.alibaba.fastjson.JSONObject;
import com.chinahuik.KubernetesManager.bean.BaseModel;
import com.chinahuik.utils.ParseUtil;

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
 * @since 2020-06-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "KubeContainer", description = "")
public class KubeContainer extends BaseModel {

	private Integer clusterId;

	private String containerType;

	private String podName;

	private String namespace;

	private String name;

	private String image;

	private String resources;
	private Long   cpuRequest;
	private Long   cpuLimit;
	private Long   memoryRequest;
	private Long   memoryLimit;
	private Long   gpuRequest;
	private Long   gpuLimit;
	private Long   gmemRequest;
	private Long   gmemLimit;

	private String volumeMounts;

	private String imagePullPolicy;

	private Date startTime;

	private Boolean ready;

	private Integer restartCount;

	private String imageId;

	private String containerId;

	private Boolean started;

	private String phase;

	private Integer lastTerminatedExitCode;

	private String lastTerminatedReason;

	private Date lastTerminatedStartAt;

	private Date lastTerminatedFinishedAt;

	public static KubeContainer parseInfoFromJson(JSONObject jsonObject) {
		final KubeContainer container = new KubeContainer();
		container.setCreatedBy("system");
		container.setUpdatedBy("system");
		container.setCreateTime(new Date());
		container.setUpdateTime(new Date());
		container.setName(jsonObject.getString("name"));
		container.setImage(jsonObject.getString("image"));
		container.setResources(jsonObject.getString("resources"));
		container.setVolumeMounts(jsonObject.getString("volumeMounts"));
		container.setImagePullPolicy(jsonObject.getString("imagePullPolicy"));
		final JSONObject resources = jsonObject.getJSONObject("resources");
		if (resources.containsKey("requests")) {
			final JSONObject requests = resources.getJSONObject("requests");
			container.setCpuRequest(ParseUtil.parseCpuLong(requests.getString("cpu")));
			container.setMemoryRequest(ParseUtil.parseLong(requests.getString("memory")));
			container.setGpuRequest(ParseUtil.parseCpuLong(requests.getString("nvidia.com/gpu")));
		}
		if (resources.containsKey("limits")) {
			final JSONObject limits = resources.getJSONObject("limits");
			container.setCpuLimit(ParseUtil.parseCpuLong(limits.getString("cpu")));
			container.setMemoryLimit(ParseUtil.parseLong(limits.getString("memory")));
			container.setGpuLimit(ParseUtil.parseCpuLong(limits.getString("nvidia.com/gpu")));
		}
		return container;
	}

	public static KubeContainer parseStatusFromJson(JSONObject jsonObject) {
		final KubeContainer container = new KubeContainer();
		container.setName(jsonObject.getString("name"));
		container.setStarted(jsonObject.getBoolean("started"));
		container.setReady(jsonObject.getBoolean("ready"));
		container.setRestartCount(jsonObject.getInteger("restartCount"));
		container.setImageId(jsonObject.getString("imageID"));
		container.setContainerId(jsonObject.getString("containerID"));
		final JSONObject state = jsonObject.getJSONObject("state");
		if (state.containsKey("running")) {
			container.setPhase("running");
			final JSONObject running = state.getJSONObject("running");
			container.setStartTime(running.getDate("startedAt"));
		}
		final JSONObject lastState = jsonObject.getJSONObject("lastState");
		if (lastState.containsKey("terminated")) {
			final JSONObject terminated = lastState.getJSONObject("terminated");
			container.setLastTerminatedExitCode(terminated.getInteger("exitCode"));
			container.setLastTerminatedReason(terminated.getString("reason"));
			container.setLastTerminatedStartAt(terminated.getDate("startedAt"));
			container.setLastTerminatedFinishedAt(terminated.getDate("finishedAt"));
		}
		return container;
	}

	public void updateStatus(KubeContainer container) {
		this.started                  = container.started;
		this.ready                    = container.ready;
		this.restartCount             = container.restartCount;
		this.imageId                  = container.imageId;
		this.containerId              = container.containerId;
		this.startTime                = container.startTime;
		this.lastTerminatedExitCode   = container.lastTerminatedExitCode;
		this.lastTerminatedFinishedAt = container.lastTerminatedFinishedAt;
		this.lastTerminatedReason     = container.lastTerminatedReason;
		this.lastTerminatedStartAt    = container.lastTerminatedStartAt;
		this.phase                    = container.phase;
	}

}
