/**
 * 
 */
package com.chinahuik.KubernetesManager.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author open-source@chinahuik.com
 *
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BaseRequest {
	private String appId;
	private String sessionId;
	private String sign;
	private String src;
	private String stamp;
	private String language;
	private String format;
	private String body;
}
