package com.chinahuik.KubernetesManager.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinahuik.KubernetesManager.bean.BaseResponse;
import com.chinahuik.KubernetesManager.bean.ListResponse;
import com.chinahuik.KubernetesManager.bean.ObjectResponse;
import com.chinahuik.KubernetesManager.form.LoginForm;
import com.chinahuik.KubernetesManager.model.UserInfoModel;
import com.chinahuik.KubernetesManager.service.UserInfoService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author open-source@chinahuik.com
 * @since 2019-08-15
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserInfoController {

	@Autowired
	private UserInfoService service;

	@ApiOperation(value = "删除UserInfoModel", notes = "的REST接口")
	@ApiImplicitParam(name = "form", value = "UserInfoModel编号", required = true,
			dataType = "LoginForm", dataTypeClass = LoginForm.class, paramType = "body")
	@RequestMapping(value = "/login.do", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse auth(@ApiParam @RequestBody @Validated LoginForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			final BaseResponse baseResponse = service.auth(form, request, response);

			return baseResponse;
		} catch (final Exception e) {
			log.error("auth failed {}", e);
			return new BaseResponse("999999", e.getMessage());
		}
	}

	@ApiOperation(value = "删除UserInfoModel", notes = "的REST接口")
	@ApiImplicitParam(name = "id", value = "UserInfoModel编号", required = true,
			dataType = "String", paramType = "path")
	@RequestMapping(value = "/delete.do", method = RequestMethod.GET)
	@ResponseBody
	public BaseResponse delete(@ApiParam @RequestParam("id") String id) {
		try {
			service.removeById(id);
			final Wrapper<UserInfoModel> entity = new QueryWrapper<UserInfoModel>();
			final List<UserInfoModel> list = service
					.page(new Page<UserInfoModel>(1, 10), entity).getRecords();
			return new ListResponse<UserInfoModel>(list);
		} catch (final Exception exception) {
			log.error("delete failed {}", exception);
			return new BaseResponse("10001", exception.getMessage());
		}
	}

	@ApiOperation(value = "获取UserInfoModel详情", notes = "的REST接口")
	@ApiImplicitParam(name = "id", value = "UserInfoModel", required = true,
			dataType = "String", paramType = "query")
	@RequestMapping(value = "/getById.do", method = RequestMethod.GET)
	@ResponseBody
	public BaseResponse findOne(@ApiParam @RequestParam("id") String id) {
		try {
			final UserInfoModel record = service.getById(id);
			return new ObjectResponse<UserInfoModel>(record);
		} catch (final Exception exception) {
			log.error("find failed {}", exception);
			return new BaseResponse("10003", exception.getMessage());
		}
	}

	@ApiOperation(value = "增加UserInfoModel", notes = "表的REST接口")
	@ApiImplicitParam(name = "record", value = "", required = true,
			dataType = "UserInfoModel", dataTypeClass = UserInfoModel.class,
			paramType = "body")
	@RequestMapping(value = "/create.do", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse insert(@ApiParam @RequestBody UserInfoModel record) {
		try {
			service.save(record);
			return new ObjectResponse<UserInfoModel>(record);
		} catch (final Exception exception) {
			log.error("insert failed {}", exception);
			return new BaseResponse("10002", exception.getMessage());
		}
	}

	@ApiOperation(value = "获取UserInfoModel列表", notes = "表的REST接口")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "currentPage", defaultValue = "1", value = "当前页",
					required = true, dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "pageSize", defaultValue = "10", value = "页面大小",
					required = true, dataType = "int", paramType = "query") })
	@RequestMapping(value = "/list.do", method = RequestMethod.GET)
	@ResponseBody
	public BaseResponse list(
			@ApiParam @RequestParam(value = "currentPage",
					defaultValue = "1") Integer currentPage,
			@ApiParam @RequestParam(value = "pageSize",
					defaultValue = "10") Integer pageSize) {
		try {
			final BaseResponse response = service.getPage(currentPage, pageSize);
			return response;
		} catch (final Exception exception) {
			log.error("get failed {}", exception);
			return new BaseResponse("10001", exception.getMessage());
		}
	}

	@ApiOperation(value = "删除UserInfoModel", notes = "的REST接口")
	@RequestMapping(value = "/logout.do", method = RequestMethod.GET)
	@ResponseBody
	public BaseResponse logout(HttpServletRequest request, HttpServletResponse response) {
		try {
			final BaseResponse baseResponse = service.logout(request, response);
			return baseResponse;
		} catch (final Exception e) {
			log.error("auth failed {}", e);
			return new BaseResponse("999999", e.getMessage());
		}
	}

	@ApiOperation(value = "更新UserInfoModel", notes = "的REST接口")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "UserInfoModel编号", required = true,
					dataType = "String", paramType = "path"),
			@ApiImplicitParam(name = "record", value = "UserInfoModel", required = true,
					dataType = "UserInfoModel", dataTypeClass = UserInfoModel.class,
					paramType = "body") })
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public BaseResponse save(@ApiParam @PathVariable String id,
			@ApiParam @RequestBody UserInfoModel record) {
		try {
			service.updateById(record);
			return new ObjectResponse<UserInfoModel>(record);
		} catch (final Exception exception) {
			log.error("update failed {}", exception);
			return new BaseResponse("10004", exception.getMessage());
		}
	}
}
