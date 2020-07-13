package com.chinahuik.KubernetesManager.dto;

import lombok.Data;

@Data
public class KubeNodeStatDto {
	private Integer clusterId;
	private String nodeName;
	private String hostIp;
	private Long cpuRequest;
	private Long cpuLimit;
	private Long memoryRequest;
	private Long memoryLimit;
	private Long gpuRequest;
	private Long gpuLimit;
	private Long gmemRequest;
	private Long gmemLimit;
}
