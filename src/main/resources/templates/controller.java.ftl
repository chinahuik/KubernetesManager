package ${package.Controller};

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
<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>
import ${package.Service}.${table.serviceName};
import com.chinahuik.KubernetesManager.bean.BaseResponse;
import com.chinahuik.KubernetesManager.bean.ListResponse;
import com.chinahuik.KubernetesManager.bean.ObjectResponse;
import ${package.Entity}.${entity};

<#if swagger2>
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
</#if>

/**
 * <p>
 * ${table.comment!} 前端控制器
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping("<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
public class ${table.controllerName} {
</#if>
	protected static final Logger logger = LoggerFactory.getLogger(${table.controllerName}.class);
    
	@Autowired
	private ${table.serviceName} service;
	
<#if swagger2>
	@ApiOperation(value = "获取${entity}列表", notes = "${table.comment!}表的REST接口")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "currentPage", defaultValue = "1", value = "当前页", required = true, dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "pageSize", defaultValue = "10", value = "页面大小", required = true, dataType = "int", paramType = "query") })
</#if>
	@RequestMapping(value = "/list.do", method = RequestMethod.GET)
	@ResponseBody
	public BaseResponse list(@ApiParam @RequestParam(value = "currentPage") Integer currentPage,@ApiParam @RequestParam(value = "pageSize") Integer pageSize) {
		try {
			Wrapper<${entity}> entity = new QueryWrapper<${entity}>();
			List<${entity}> list = service.page(new Page<${entity}>(currentPage,pageSize), entity).getRecords();
			int total = service.count(entity);
			BaseResponse response = new ListResponse<${entity}>(list);
			response.put("total", total);
			return response;
		} catch (Exception exception) {
			logger.error("list ${entity} failed {}", exception);
			return new BaseResponse("10001", exception.getMessage());
		}
	}

<#if swagger2>
	@ApiOperation(value = "增加${entity}", notes = "${table.comment!}表的REST接口")
	@ApiImplicitParam(name = "record", value = "${table.comment!}", required = true, dataType = "${entity}",dataTypeClass=${entity}.class, paramType = "body")
</#if>
	@RequestMapping(value = "/create.do", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse create(@ApiParam @RequestBody ${entity} record) {
		try {
			service.save(record);
			return BaseResponse.successResponse("创建成功！");
		} catch (Exception exception) {
			logger.error("insert failed {}", exception);
			return new BaseResponse("10002", exception.getMessage());
		}
	}

<#if swagger2>
	@ApiOperation(value = "获取${entity}详情", notes = "${table.comment!}的REST接口")
	@ApiImplicitParam(name = "id", value = "${entity}", required = true, dataType = "Integer", paramType = "query")
</#if>
	@RequestMapping(value = "/getById.do", method = RequestMethod.GET)
	@ResponseBody
	public BaseResponse findOne(@ApiParam @RequestParam(value = "id") Integer id) {
		try {
			${entity} record = service.getById(id);
			return new ObjectResponse<${entity}>(record);
		} catch (Exception exception) {
			logger.error("find ${entity} failed {}", exception);
			return new BaseResponse("10003", exception.getMessage());
		}
	}

<#if swagger2>
	@ApiOperation(value = "更新${entity}", notes = "${table.comment!}的REST接口")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "${entity}编号", required = true, dataType = "Integer", paramType = "query"),
			@ApiImplicitParam(name = "record", value = "${entity}", required = true, dataType = "${entity}",dataTypeClass=${entity}.class) })
</#if>
	@RequestMapping(value = "/update.do", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse save(@ApiParam @RequestParam(value = "id") Integer id,@ApiParam @ModelAttribute ${entity} record) {
		try {
			service.updateById(record);
			return BaseResponse.successResponse("修改成功！");
		} catch (Exception exception) {
			logger.error("update failed {}", exception);
			return new BaseResponse("10004", exception.getMessage());
		}
	}

<#if swagger2>
	@ApiOperation(value = "删除${entity}", notes = "${table.comment!}的REST接口")
	@ApiImplicitParam(name = "id", value = "${entity}编号", required = true, dataType = "Integer", paramType = "query")
</#if>
	@RequestMapping(value = "/delete.do", method = RequestMethod.GET)
	@ResponseBody
	public BaseResponse delete(@ApiParam @RequestParam(value = "id") Integer id) {
		try {
			service.removeById(id);
			return BaseResponse.successResponse("删除成功！");
		} catch (Exception exception) {
			logger.error("delete failed {}", exception);
			return new BaseResponse("10001", exception.getMessage());
		}
	}

}
</#if>
