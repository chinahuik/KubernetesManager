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
import com.chinahuik.KubernetesManager.model.KubeJob;
import com.chinahuik.KubernetesManager.service.KubeJobService;

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
 * @since 2020-06-17
 */
@RestController
@RequestMapping("/KubernetesManager/kube-job")
public class KubeJobController {
	@Autowired
	private KubeJobService service;

	protected static final Logger logger = LoggerFactory
			.getLogger(KubeJobController.class);

	@ApiOperation(value = "增加KubeJob", notes = "表的REST接口")
	@ApiImplicitParam(name = "record", value = "", required = true, dataType = "KubeJob",
			dataTypeClass = KubeJob.class, paramType = "body")
	@RequestMapping(value = "/create.do", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse create(@ApiParam @RequestBody KubeJob record) {
		try {
			service.save(record);
			return BaseResponse.successResponse("创建成功！");
		} catch (final Exception exception) {
			logger.error("insert failed {}", exception);
			return new BaseResponse("10002", exception.getMessage());
		}
	}

	@ApiOperation(value = "删除KubeJob", notes = "的REST接口")
	@ApiImplicitParam(name = "id", value = "KubeJob编号", required = true,
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

	@ApiOperation(value = "获取KubeJob详情", notes = "的REST接口")
	@ApiImplicitParam(name = "id", value = "KubeJob", required = true,
			dataType = "Integer", paramType = "query")
	@RequestMapping(value = "/getById.do", method = RequestMethod.GET)
	@ResponseBody
	public BaseResponse findOne(@ApiParam @RequestParam(value = "id") Integer id) {
		try {
			final KubeJob record = service.getById(id);
			return new ObjectResponse<KubeJob>(record);
		} catch (final Exception exception) {
			logger.error("find KubeJob failed {}", exception);
			return new BaseResponse("10003", exception.getMessage());
		}
	}

	@ApiOperation(value = "获取KubeJob列表", notes = "表的REST接口")
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
			final Wrapper<KubeJob> entity = new QueryWrapper<KubeJob>();
			final List<KubeJob> list = service
					.page(new Page<KubeJob>(currentPage, pageSize), entity).getRecords();
			final int total = service.count(entity);
			final BaseResponse response = new ListResponse<KubeJob>(list);
			response.put("total", total);
			return response;
		} catch (final Exception exception) {
			logger.error("list KubeJob failed {}", exception);
			return new BaseResponse("10001", exception.getMessage());
		}
	}

	@ApiOperation(value = "更新KubeJob", notes = "的REST接口")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "KubeJob编号", required = true,
					dataType = "Integer", paramType = "query"),
			@ApiImplicitParam(name = "record", value = "KubeJob", required = true,
					dataType = "KubeJob", dataTypeClass = KubeJob.class) })
	@RequestMapping(value = "/update.do", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse save(@ApiParam @RequestParam(value = "id") Integer id,
			@ApiParam @ModelAttribute KubeJob record) {
		try {
			service.updateById(record);
			return BaseResponse.successResponse("修改成功！");
		} catch (final Exception exception) {
			logger.error("update failed {}", exception);
			return new BaseResponse("10004", exception.getMessage());
		}
	}

}
