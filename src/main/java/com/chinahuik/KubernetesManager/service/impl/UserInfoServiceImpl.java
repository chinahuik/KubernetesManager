package com.chinahuik.KubernetesManager.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chinahuik.KubernetesManager.bean.BaseResponse;
import com.chinahuik.KubernetesManager.bean.ListResponse;
import com.chinahuik.KubernetesManager.bean.UserPrincipal;
import com.chinahuik.KubernetesManager.dao.UserInfoDao;
import com.chinahuik.KubernetesManager.form.LoginForm;
import com.chinahuik.KubernetesManager.model.UserInfoModel;
import com.chinahuik.KubernetesManager.service.CacheService;
import com.chinahuik.KubernetesManager.service.UserInfoService;
import com.chinahuik.utils.CookieUtil;
import com.chinahuik.utils.HttpSessionUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author open-source@chinahuik.com
 * @since 2019-08-15
 */
@Service
@Slf4j
public class UserInfoServiceImpl extends ServiceImpl<UserInfoDao, UserInfoModel>
		implements UserInfoService {
	@Autowired
	private CacheService cacheService;

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.chinahuik.wechat.user.service.UserInfoService#auth(com.chinahuik.wechat.
	 * user.form.LoginForm, javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public BaseResponse auth(LoginForm form, HttpServletRequest request,
			HttpServletResponse response) {
		log.info("auth request: {}", form);
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put("username", form.getUsername());
		final List<UserInfoModel> models = baseMapper.selectByMap(params);
		if (models == null || models.isEmpty()) {
			return BaseResponse.failedResponse("650001", "没有此用户");
		}
		final UserInfoModel userInfoModel = models.get(0);
		final String encPass = form.getPassword();
		// String password = cryptoService.sm2Decrypt( encPass);
		// String md5Pass = MD5Utils.md5(password);
		if (encPass.equals(userInfoModel.getPassMd5())) {
			saveLogin(response, userInfoModel);
			return BaseResponse.successResponse();
		}
		return BaseResponse.failedResponse("650002", "鉴权失败");
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.chinahuik.wechat.user.service.UserInfoService#getPage(java.lang.Integer,
	 * java.lang.Integer)
	 */
	@Override
	public BaseResponse getPage(Integer start, Integer num) {
		final Wrapper<UserInfoModel> entity = new QueryWrapper<UserInfoModel>();
		final List<UserInfoModel> list = baseMapper
				.selectPage(new Page<UserInfoModel>(start, num), entity).getRecords();
		final ListResponse<UserInfoModel> response = new ListResponse<UserInfoModel>(
				list);
		final int total = baseMapper.selectCount(entity);
		response.put("total", total);
		return response;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.chinahuik.wechat.user.service.UserInfoService#logout(java.lang.String,
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public BaseResponse logout(HttpServletRequest request, HttpServletResponse response) {
		try {
			clearLogin(request, response);
			return BaseResponse.successResponse();
		} catch (final Exception e) {
			log.error("logout failed: {}", e);
			return BaseResponse.failedResponse(e.getMessage());
		}

	}

	/**
	 * 清除所有登录的信息，包括cookie，session
	 *
	 * @param request
	 * @param response
	 */
	private void clearLogin(HttpServletRequest request, HttpServletResponse response) {
		final Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (final Cookie cookie : cookies) {
				final String name = cookie.getName();
				// if (name.equals("JSESSIONID")) {
				final Cookie killMyCookie = new Cookie(name, null);
				killMyCookie.setMaxAge(0);
				killMyCookie.setPath("/");
				response.addCookie(killMyCookie);
				// }
			}
		}
		response.setHeader("refresh", "0");
		final HttpSessionUtil sessionUtil = new HttpSessionUtil(request);
		sessionUtil.removeAttribute("user");
		sessionUtil.removeAttribute("loginUserId");
		sessionUtil.removeAttribute("principal");
		sessionUtil.removeAttribute("requestId");
		final Integer userid = UserPrincipal.getUserId();
		if (userid != null) {
			cacheService.removeBean(userid.toString());
		}
	}

	/**
	 * TODO
	 *
	 * @Author: open-source@chinahuik.com
	 * @Time: 2019年12月7日 下午11:32:26
	 * @param response
	 * @param userInfoModel
	 *
	 */
	private void saveLogin(HttpServletResponse response, UserInfoModel userInfoModel) {
		addCookies(response, userInfoModel);
		addHeaders(response, userInfoModel);
		cacheService.cacheBean(userInfoModel.getId().toString(), userInfoModel,
				2 * 60 * 60);
	}

	/**
	 * @param response
	 * @param userInfoModel
	 */
	private static void addCookies(HttpServletResponse response,
			UserInfoModel userInfoModel) {
		CookieUtil.addCookie(response, "x-token", userInfoModel.getId().toString(), "",
				-1, false, false);
		CookieUtil.addCookie(response, "a_user_name", userInfoModel.getUsername(), "", -1,
				false, false);
	}

	/**
	 * @param response
	 * @param userInfoModel
	 */
	private static void addHeaders(HttpServletResponse response,
			UserInfoModel userInfoModel) {
		// TODO Auto-generated method stub

	}

}
