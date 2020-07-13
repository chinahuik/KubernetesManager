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
import com.chinahuik.KubernetesManager.form.SystemConfigForm;
import com.chinahuik.KubernetesManager.model.SystemConfigModel;
import com.chinahuik.KubernetesManager.service.SystemConfigService;

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
@RequestMapping("/config/item")
public class SystemConfigController {
	@Autowired
	private SystemConfigService service;

	protected static final Logger logger = LoggerFactory
			.getLogger(SystemConfigController.class);

	@ApiOperation(value = "删除SystemConfigModel", notes = "的REST接口")
	@ApiImplicitParam(name = "id", value = "SystemConfigModel编号", required = true,
			dataType = "int", paramType = "query")
	@RequestMapping(value = "/delete.do", method = RequestMethod.GET)
	@ResponseBody
	public BaseResponse delete(@ApiParam @RequestParam("id") Integer id) {
		try {
			service.removeById(id);
			final Wrapper<SystemConfigModel> entity = new QueryWrapper<SystemConfigModel>();
			final List<SystemConfigModel> list = service
					.page(new Page<SystemConfigModel>(1, 10), entity).getRecords();
			return new ListResponse<SystemConfigModel>(list);
		} catch (final Exception exception) {
			logger.error("delete failed {}", exception);
			return new BaseResponse("10001", exception.getMessage());
		}
	}

	@ApiOperation(value = "增加SystemConfigModel", notes = "表的REST接口")
	@ApiImplicitParam(name = "record", value = "", required = true,
			dataType = "SystemConfigModel", dataTypeClass = SystemConfigForm.class,
			paramType = "body")
	@RequestMapping(value = "/edit.do", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse edit(@ApiParam @RequestBody SystemConfigForm record) {
		try {
			service.update(record);
			return BaseResponse.successResponse("增加成功！");
		} catch (final Exception exception) {
			logger.error("insert failed {}", exception);
			return new BaseResponse("10002", exception.getMessage());
		}
	}

	@ApiOperation(value = "获取SystemConfigModel详情", notes = "的REST接口")
	@ApiImplicitParam(name = "id", value = "SystemConfigModel", required = true,
			dataType = "int", paramType = "path")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public BaseResponse findOne(@ApiParam @PathVariable Integer id) {
		try {
			final SystemConfigModel record = service.getById(id);
			return new ObjectResponse<SystemConfigModel>(record);
		} catch (final Exception exception) {
			logger.error("find failed {}", exception);
			return new BaseResponse("10003", exception.getMessage());
		}
	}

	@ApiOperation(value = "增加SystemConfigModel", notes = "表的REST接口")
	@ApiImplicitParam(name = "record", value = "", required = true,
			dataType = "SystemConfigModel", dataTypeClass = SystemConfigForm.class,
			paramType = "body")
	@RequestMapping(value = "/add.do", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse insert(@ApiParam @RequestBody SystemConfigForm record) {
		try {
			service.save(record);
			return BaseResponse.successResponse("增加成功！");
		} catch (final Exception exception) {
			logger.error("insert failed {}", exception);
			return new BaseResponse("10002", exception.getMessage());
		}
	}

	@ApiOperation(value = "获取SystemConfigModel列表", notes = "表的REST接口")
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
			final Wrapper<SystemConfigModel> entity = new QueryWrapper<SystemConfigModel>();
			final List<SystemConfigModel> list = service
					.page(new Page<SystemConfigModel>(currentPage, pageSize), entity)
					.getRecords();
			return new ListResponse<SystemConfigModel>(list);
		} catch (final Exception exception) {
			logger.error("get failed {}", exception);
			return new BaseResponse("10001", exception.getMessage());
		}
	}

	@ApiOperation(value = "获取SystemConfigModel列表", notes = "表的REST接口")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "start", defaultValue = "0", value = "起始位置",
					required = true, dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "num", defaultValue = "10", value = "数量",
					required = true, dataType = "int", paramType = "query") })
	@RequestMapping(value = "/listByGroup.do", method = RequestMethod.GET)
	@ResponseBody
	public BaseResponse listByGroup(@ApiParam @RequestParam(value = "id") Integer id) {
		try {
			final List<SystemConfigModel> list = service.getRecords(id);
			return new ListResponse<SystemConfigModel>(list);
		} catch (final Exception exception) {
			logger.error("get failed {}", exception);
			return new BaseResponse("10001", exception.getMessage());
		}
	}

	@ApiOperation(value = "更新SystemConfigModel", notes = "的REST接口")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "SystemConfigModel编号", required = true,
					dataType = "int", paramType = "path"),
			@ApiImplicitParam(name = "record", value = "SystemConfigModel",
					required = true, dataType = "SystemConfigModel",
					dataTypeClass = SystemConfigModel.class) })
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public BaseResponse save(@ApiParam @PathVariable Integer id,
			@ApiParam @ModelAttribute SystemConfigModel record) {
		try {
			service.updateById(record);
			return new ObjectResponse<SystemConfigModel>(record);
		} catch (final Exception exception) {
			logger.error("update failed {}", exception);
			return new BaseResponse("10004", exception.getMessage());
		}
	}

}
