package com.chinahuik.KubernetesManager.model;

import com.alibaba.fastjson.JSONArray;
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
 * @since 2020-06-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "KubeNode", description = "")
public class KubeNode extends BaseModel {

	private Integer clusterId;

	private String uid;

	private String name;

	private String resourceVersion;

	private String annotations;

	private String selfLink;

	private String labels;

	private String podCidr;

	private Long cpuCap;

	private Long gpuCap;

	private Long memoryCap;

	private Long gmemCap;

	private Long ephemeralStorageCap;

	private Integer podsCap;

	private Integer hugepages1gCap;

	private Integer hugepages2mCap;

	private Long cpuAllocatable;

	private Long gpuAllocatable;

	private Long memoryAllocatable;

	private Long gmemAllocatable;

	private Long ephemeralStorageAllocatable;

	private Integer podsAllocatable;

	private Integer hugepages1gAllocatable;

	private Integer hugepages2mAllocatable;

	private String internalIp;

	private String hostName;

	private String machineId;

	private String bootId;

	private String kernelVersion;

	private String containerRuntimeVersion;

	private String kubeVersion;

	private String systemUuid;

	private String kubeProxyVersion;

	private String operatingSystem;

	private String osImage;

	private String architecture;

	private String networkUnavailable;

	private String networkReason;

	private String memoryPressure;

	private String memoryReason;

	private String networkMessage;

	private String memoryMessage;

	private String diskPressure;

	private String diskReason;

	private String diskMessage;

	private String pidPressure;

	private String pidReason;

	private String pidMessage;

	private String kubeReady;

	private String kubeReason;

	private String kubeMessage;

	public static KubeNode parseFromJson(JSONObject object) {
		final JSONObject metadata = object.getJSONObject("metadata");
		final JSONObject spec = object.getJSONObject("spec");
		final JSONObject status = object.getJSONObject("status");
		KubeNode item = new KubeNode();
		item = parseMetadata(item, metadata);
		item = parseSpec(item, spec);
		item = parseStatus(item, status);
		return item;
	}

	private static KubeNode parseMetadata(KubeNode item, JSONObject metadata) {
		item.setCreatedBy("system");
		item.setUpdatedBy("system");
		item.setAnnotations(metadata.getString("annotations"));
		item.setUid(metadata.getString("uid"));
		item.setResourceVersion(metadata.getString("resourceVersion"));
		item.setName(metadata.getString("name"));
		item.setCreateTime(metadata.getDate("creationTimestamp"));
		item.setSelfLink(metadata.getString("selfLink"));
		item.setLabels(metadata.getString("labels"));
		return item;
	}

	private static KubeNode parseNodeStatusAddresses(KubeNode node, JSONArray addresses) {
		for (int i = 0; i < addresses.size(); i++) {
			final JSONObject item = addresses.getJSONObject(i);
			final String type = item.getString("type");
			final String value = item.getString("address");
			if (type.equalsIgnoreCase("hostname")) {
				node.setHostName(value);
			} else if (type.equalsIgnoreCase("InternalIP")) {
				node.setInternalIp(value);
			}
		}
		return node;
	}

	private static KubeNode parseNodeStatusAllocatable(KubeNode node,
			JSONObject allocatable) {
		node.setCpuAllocatable(allocatable.getLong("cpu") * 1000);
		node.setGpuAllocatable(allocatable.getLong("nvidia.com/gpu"));
		node.setEphemeralStorageAllocatable(
				ParseUtil.parseLong(allocatable.getString("ephemeral-storage")));
		node.setMemoryAllocatable(ParseUtil.parseLong(allocatable.getString("memory")));
		node.setPodsAllocatable(allocatable.getInteger("pods"));
		node.setHugepages1gAllocatable(allocatable.getInteger("hugepages-1Gi"));
		node.setHugepages2mAllocatable(allocatable.getInteger("hugepages-2Mi"));
		return node;
	}

	private static KubeNode parseNodeStatusCapacity(KubeNode node, JSONObject capacity) {
		node.setCpuCap(capacity.getLong("cpu") * 1000);
		node.setGpuCap(capacity.getLong("nvidia.com/gpu"));
		node.setEphemeralStorageCap(
				ParseUtil.parseLong(capacity.getString("ephemeral-storage")));
		node.setMemoryCap(ParseUtil.parseLong(capacity.getString("memory")));
		node.setPodsCap(capacity.getInteger("pods"));
		node.setHugepages1gCap(capacity.getInteger("hugepages-1Gi"));
		node.setHugepages2mCap(capacity.getInteger("hugepages-2Mi"));
		return node;
	}

	private static KubeNode parseNodeStatusConditions(KubeNode node,
			JSONArray conditions) {
		for (int i = 0; i < conditions.size(); i++) {
			final JSONObject condition = conditions.getJSONObject(i);
			final String reason = condition.getString("reason");
			final String type = condition.getString("type");
			final String message = condition.getString("message");
			final String status = condition.getString("status");
			if (type.equalsIgnoreCase("Ready")) {
				node.setKubeMessage(message);
				node.setKubeReady(status);
				node.setKubeReason(reason);
			} else if (type.equalsIgnoreCase("PIDPressure")) {
				node.setPidMessage(message);
				node.setPidPressure(status);
				node.setPidReason(reason);
			} else if (type.equalsIgnoreCase("DiskPressure")) {
				node.setDiskMessage(message);
				node.setDiskPressure(status);
				node.setDiskReason(reason);
			} else if (type.equalsIgnoreCase("MemoryPressure")) {
				node.setMemoryMessage(message);
				node.setMemoryPressure(status);
				node.setMemoryReason(reason);
			} else if (type.equalsIgnoreCase("NetworkUnavailable")) {
				node.setNetworkMessage(message);
				node.setNetworkUnavailable(status);
				node.setNetworkReason(reason);
			}
		}
		return node;
	}

	private static KubeNode parseNodeStatusInfo(KubeNode node, JSONObject nodeInfo) {
		node.setMachineId(nodeInfo.getString("machineID"));
		node.setBootId(nodeInfo.getString("bootID"));
		node.setKernelVersion(nodeInfo.getString("kernelVersion"));
		node.setContainerRuntimeVersion(nodeInfo.getString("containerRuntimeVersion"));
		node.setKubeVersion(nodeInfo.getString("kubeletVersion"));
		node.setSystemUuid(nodeInfo.getString("systemUUID"));
		node.setKubeProxyVersion(nodeInfo.getString("kubeProxyVersion"));
		node.setOperatingSystem(nodeInfo.getString("operatingSystem"));
		node.setOsImage(nodeInfo.getString("osImage"));
		node.setArchitecture(nodeInfo.getString("architecture"));

		return node;
	}

	private static KubeNode parseSpec(KubeNode item, JSONObject spec) {
		item.setPodCidr(spec.getString("podCIDR"));
		return item;
	}

	private static KubeNode parseStatus(KubeNode node, JSONObject status) {
		final JSONObject allocatable = status.getJSONObject("allocatable");
		node = parseNodeStatusAllocatable(node, allocatable);
		final JSONArray addresses = status.getJSONArray("addresses");
		node = parseNodeStatusAddresses(node, addresses);
		final JSONObject nodeInfo = status.getJSONObject("nodeInfo");
		node = parseNodeStatusInfo(node, nodeInfo);
		final JSONObject capacity = status.getJSONObject("capacity");
		node = parseNodeStatusCapacity(node, capacity);
		final JSONArray conditions = status.getJSONArray("conditions");
		node = parseNodeStatusConditions(node, conditions);
		return node;
	}

}
