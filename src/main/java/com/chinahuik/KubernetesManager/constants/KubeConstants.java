package com.chinahuik.KubernetesManager.constants;

public class KubeConstants {
	public enum ReleaseStatus {
		DEPLOYING("00", "部署中"), DEPLOY_SUCCESS("01", "启动中"), RUNNING("02", "运行中"),
		RUNNING_ERROR("02", "运行异常"), DEPLOY_FAILED("03", "部署失败"),
		RESOURCE_SHORT("03", "资源不足"), PERMISSION_DENIED("03", "权限错误"),
		DEPLOY_TIMEOUT("03", "启动超时"), STOPPING("04", "停止中"), STOPPED("05", "已停止");

		private final String code;
		private final String name;

		private ReleaseStatus(String code, String name) {
			this.code = code;
			this.name = name;
		}

		public String getCode() {
			return this.code;
		}

		public String getName() {
			return this.name;
		}
	}
}
