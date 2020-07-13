package com.chinahuik.KubernetesManager.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chinahuik.KubernetesManager.bean.BaseResponse;
import com.chinahuik.KubernetesManager.form.LoginForm;
import com.chinahuik.KubernetesManager.model.UserInfoModel;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author open-source@chinahuik.com
 * @since 2019-08-15
 */
public interface UserInfoService extends IService<UserInfoModel> {

	/**
	 * @param form
	 * @param response
	 * @param request
	 * @return
	 */
	BaseResponse auth(LoginForm form, HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * TODO
	 *
	 * @Author: open-source@chinahuik.com
	 * @Time: 2020年1月3日 下午11:33:07
	 * @param start
	 * @param num
	 * @return
	 *
	 */
	BaseResponse getPage(Integer start, Integer num);

	/**
	 * TODO
	 *
	 * @Author: open-source@chinahuik.com
	 * @Time: 2019年12月23日 上午12:46:58
	 * @param request
	 * @param response
	 * @return
	 *
	 */
	BaseResponse logout(HttpServletRequest request, HttpServletResponse response);

}
