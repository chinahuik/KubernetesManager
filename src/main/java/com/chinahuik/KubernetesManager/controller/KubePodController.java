package com.chinahuik.KubernetesManager.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
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
import com.chinahuik.KubernetesManager.model.KubePod;
import com.chinahuik.KubernetesManager.service.KubePodService;

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
 * @since 2020-06-11
 */
@RestController
@RequestMapping("/KubernetesManager/kube-pod")
public class KubePodController {
	@Autowired
	private KubePodService service;

	protected static final Logger logger = LoggerFactory
			.getLogger(KubePodController.class);

	@ApiOperation(value = "增加KubePod", notes = "表的REST接口")
	@ApiImplicitParam(name = "record", value = "", required = true, dataType = "KubePod",
			dataTypeClass = KubePod.class, paramType = "body")
	@RequestMapping(value = "/create.do", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse create(@ApiParam @RequestBody KubePod record) {
		try {
			service.save(record);
			return BaseResponse.successResponse("创建成功！");
		} catch (final Exception exception) {
			logger.error("insert failed {}", exception);
			return new BaseResponse("10002", exception.getMessage());
		}
	}

	@ApiOperation(value = "删除KubePod", notes = "的REST接口")
	@ApiImplicitParam(name = "id", value = "KubePod编号", required = true,
			dataType = "Integer", paramType = "query")
	@RequestMapping(value = "/delete.do", method = RequestMethod.GET)
	@ResponseBody
	public BaseResponse delete(@ApiParam @RequestParam(value = "id") Integer id) {
		try {
			service.removeById(id);
			return BaseResponse.successResponse("删除成功！");
		} catch (final Exception exception) {
			logger.error("delete failed {}", exception);
			return new BaseResponse("10001", exception.getMessage());
		}
	}

	@ApiOperation(value = "获取KubePod详情", notes = "的REST接口")
	@ApiImplicitParam(name = "id", value = "KubePod", required = true,
			dataType = "Integer", paramType = "query")
	@RequestMapping(value = "/getById.do", method = RequestMethod.GET)
	@ResponseBody
	public BaseResponse findOne(@ApiParam @RequestParam(value = "id") Integer id) {
		try {
			final KubePod record = service.getById(id);
			return new ObjectResponse<KubePod>(record);
		} catch (final Exception exception) {
			logger.error("find KubePod failed {}", exception);
			return new BaseResponse("10003", exception.getMessage());
		}
	}

	@ApiOperation(value = "获取KubePod列表", notes = "表的REST接口")
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
			final Wrapper<KubePod> entity = new QueryWrapper<KubePod>();
			final List<KubePod> list = service
					.page(new Page<KubePod>(currentPage, pageSize), entity).getRecords();
			final int total = service.count(entity);
			final BaseResponse response = new ListResponse<KubePod>(list);
			response.put("total", total);
			return response;
		} catch (final Exception exception) {
			logger.error("list KubePod failed {}", exception);
			return new BaseResponse("10001", exception.getMessage());
		}
	}

	@ApiOperation(value = "更新KubePod", notes = "的REST接口")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "KubePod编号", required = true,
					dataType = "Integer", paramType = "query"),
			@ApiImplicitParam(name = "record", value = "KubePod", required = true,
					dataType = "KubePod", dataTypeClass = KubePod.class) })
	@RequestMapping(value = "/update.do", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse save(@ApiParam @RequestParam(value = "id") Integer id,
			@ApiParam @ModelAttribute KubePod record) {
		try {
			service.updateById(record);
			return BaseResponse.successResponse("修改成功！");
		} catch (final Exception exception) {
			logger.error("update failed {}", exception);
			return new BaseResponse("10004", exception.getMessage());
		}
	}

}
