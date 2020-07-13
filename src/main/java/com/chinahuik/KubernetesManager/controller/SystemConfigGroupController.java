package com.chinahuik.KubernetesManager.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
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
import com.chinahuik.KubernetesManager.form.SystemConfigGroupForm;
import com.chinahuik.KubernetesManager.model.SystemConfigGroupModel;
import com.chinahuik.KubernetesManager.service.SystemConfigGroupService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author open-source@chinahuik.com
 * @since 2019-08-15
 */
@RestController
@RequestMapping("/config/group")
public class SystemConfigGroupController {
	@Autowired
	private SystemConfigGroupService service;

	protected static final Logger logger = LoggerFactory
			.getLogger(SystemConfigGroupController.class);

	@ApiOperation(value = "删除SystemConfigGroupModel", notes = "的REST接口")
	@ApiImplicitParam(name = "id", value = "SystemConfigGroupModel编号", required = true,
			dataType = "int", paramType = "query")
	@RequestMapping(value = "/delete.do", method = RequestMethod.GET)
	@ResponseBody
	public BaseResponse delete(@ApiParam @RequestParam("id") Integer id) {
		try {
			service.removeById(id);
			return BaseResponse.successResponse("删除成功!");
		} catch (final Exception exception) {
			logger.error("delete failed {}", exception);
			return new BaseResponse("10001", exception.getMessage());
		}
	}

	@ApiOperation(value = "获取SystemConfigGroupModel详情", notes = "的REST接口")
	@ApiImplicitParam(name = "id", value = "SystemConfigGroupModel", required = true,
			dataType = "int", paramType = "path")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public BaseResponse findOne(@ApiParam @PathVariable Integer id) {
		try {
			final SystemConfigGroupModel record = service.getById(id);
			return new ObjectResponse<SystemConfigGroupModel>(record);
		} catch (final Exception exception) {
			logger.error("find failed {}", exception);
			return new BaseResponse("10003", exception.getMessage());
		}
	}

	@ApiOperation(value = "增加SystemConfigGroupModel", notes = "表的REST接口")
	@ApiImplicitParam(name = "form", value = "", required = true,
			dataType = "SystemConfigGroupForm",
			dataTypeClass = SystemConfigGroupForm.class, paramType = "body")
	@RequestMapping(value = "/add.do", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse insert(@ApiParam @RequestBody SystemConfigGroupForm form) {
		try {
			service.save(form);
			return BaseResponse.successResponse("添加成功！");
		} catch (final Exception exception) {
			logger.error("insert failed {}", exception);
			return new BaseResponse("10002", exception.getMessage());
		}
	}

	@ApiOperation(value = "获取SystemConfigGroupModel列表", notes = "表的REST接口")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "currentPage", defaultValue = "1", value = "当前页",
					required = true, dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "pageSize", defaultValue = "10", value = "页面大小",
					required = true, dataType = "int", paramType = "query") })
	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseBody
	public BaseResponse list(
			@ApiParam @RequestParam(value = "currentPage",
					defaultValue = "1") Integer currentPage,
			@ApiParam @RequestParam(value = "pageSize",
					defaultValue = "10") Integer pageSize) {
		try {
			final Wrapper<SystemConfigGroupModel> entity = new QueryWrapper<SystemConfigGroupModel>();
			final List<SystemConfigGroupModel> list = service
					.page(new Page<SystemConfigGroupModel>(currentPage, pageSize), entity)
					.getRecords();
			return new ListResponse<SystemConfigGroupModel>(list);
		} catch (final Exception exception) {
			logger.error("get failed {}", exception);
			return new BaseResponse("10001", exception.getMessage());
		}
	}

	@ApiOperation(value = "获取SystemConfigGroupModel列表", notes = "表的REST接口")
	@RequestMapping(value = "list.do", method = RequestMethod.GET)
	@ResponseBody
	public BaseResponse listAll() {
		try {
			final Wrapper<SystemConfigGroupModel> entity = new QueryWrapper<SystemConfigGroupModel>();
			final List<SystemConfigGroupModel> list = service.list(entity);
			return new ListResponse<SystemConfigGroupModel>(list);
		} catch (final Exception exception) {
			logger.error("get failed {}", exception);
			return new BaseResponse("10001", exception.getMessage());
		}
	}

	@ApiOperation(value = "更新SystemConfigGroupModel", notes = "的REST接口")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "SystemConfigGroupModel编号",
					required = true, dataType = "int", paramType = "path"),
			@ApiImplicitParam(name = "record", value = "SystemConfigGroupModel",
					required = true, dataType = "SystemConfigGroupModel",
					dataTypeClass = SystemConfigGroupModel.class) })
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public BaseResponse save(@ApiParam @PathVariable Integer id,
			@ApiParam @ModelAttribute SystemConfigGroupModel record) {
		try {
			service.updateById(record);
			return new ObjectResponse<SystemConfigGroupModel>(record);
		} catch (final Exception exception) {
			logger.error("update failed {}", exception);
			return new BaseResponse("10004", exception.getMessage());
		}
	}

}
