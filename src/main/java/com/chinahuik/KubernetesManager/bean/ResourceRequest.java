package com.chinahuik.KubernetesManager.bean;

import lombok.Data;

@Data
public class ResourceRequest {
	private String requestId;
	private String ruleGroup;
	private Long cpuUsed;
	private Long gpuUsed;
	private Long memoryUsed;
	private Long gmemUsed;
	private int priority;
	private int userId;
}
