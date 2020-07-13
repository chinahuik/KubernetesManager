/**
 *
 */
package com.chinahuik.KubernetesManager.form;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

/**
 * @author open-source@chinahuik.com
 *
 */
@Data
public class LoginForm {
	@NotEmpty(message = "用户名不能为空")
	// @Min(value = 3, message = "用户名不合法")
	private String username;
	@NotEmpty(message = "密码不能为空")
	// @Min(value = 5, message = "密码不合法")
	private String password;
}
