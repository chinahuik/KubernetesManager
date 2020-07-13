/**
 * @Project: wechat-common
 * @Package: com.chinahuik.commons.bean
 * @Author: open-source@chinahuik.com
 * @Time: 2019年12月7日 下午11:13:01
 * @File: UserPrincipal.java
 *
 */
package com.chinahuik.KubernetesManager.bean;

import com.chinahuik.KubernetesManager.model.UserInfoModel;

/**
 * @author open-source@chinahuik.com
 *
 */
public class UserPrincipal {
	private static ThreadLocal<UserInfoModel> users = new ThreadLocal<>();

	/**
	 *
	 * TODO
	 *
	 * @Author: open-source@chinahuik.com
	 * @Time: 2019年12月7日 下午11:18:23
	 *
	 */
	public static void clear() {
		users.remove();
	}

	/**
	 *
	 * TODO
	 *
	 * @Author: open-source@chinahuik.com
	 * @Time: 2019年12月7日 下午11:18:27
	 * @return
	 *
	 */
	public static Integer getUserId() {
		final UserInfoModel model = getUserInfo();
		if (model == null) {
			return null;
		}
		return model.getId();
	}

	/**
	 *
	 * 获取当前用户
	 *
	 * @Author: open-source@chinahuik.com
	 * @Time: 2019年12月7日 下午11:17:30
	 * @return
	 *
	 */
	public static UserInfoModel getUserInfo() {
		return users.get();
	}

	/**
	 *
	 * TODO
	 *
	 * @Author: open-source@chinahuik.com
	 * @Time: 2019年12月17日 下午11:58:16
	 * @return
	 *
	 */
	public static String getUserName() {
		final UserInfoModel model = getUserInfo();
		if (model == null) {
			return null;
		}
		return model.getUsername();
	}

	/**
	 *
	 * TODO
	 *
	 * @Author: open-source@chinahuik.com
	 * @Time: 2019年12月7日 下午11:18:15
	 * @param model
	 *
	 */
	public static void setUserInfo(UserInfoModel model) {
		users.set(model);
	}
}
