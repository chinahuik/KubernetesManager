/**
 * 
 */
package com.chinahuik.KubernetesManager.bean;

import org.springframework.ui.ModelMap;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author open-source@chinahuik.com
 *
 */
@ApiModel(value="返回对象",description="基础返回对象")
public class BaseResponse extends ModelMap{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5393092958882492305L;
	public BaseResponse(){
    	setResponseCode("000000");
    	setResponseMsg("success");
    }
    public BaseResponse(String code,String msg){
    	setResponseCode(code);
    	setResponseMsg(msg);
    }
    @ApiModelProperty("返回码")
    public String getResponseCode() {
		return (String) this.get("responseCode");
	}
    public void setResponseCode(String code) {
		this.put("responseCode", code);
		this.put("success",code.equals("000000"));
	}
    @ApiModelProperty("返回信息")
    public String getResponseMsg() {
		return (String) this.get("responseMsg");
	}
    public void setResponseMsg(String msg) {
		this.put("responseMsg", msg);
	}
    public boolean isSuccess() {
		return (boolean) this.get("success");
	}
    public void setSuccess(boolean success) {
		this.put("success", success);
	}
    public static BaseResponse successResponse() {
		return new BaseResponse();
	}
    public static BaseResponse successResponse(String msg) {
		return new BaseResponse("000000",msg);
	}

    public static BaseResponse failedResponse() {
		return new BaseResponse("650099", "系统错误");
	}
    public static BaseResponse failedResponse(String msg) {
		return new BaseResponse("650099", msg);
	}
    public static BaseResponse failedResponse(String code,String msg) {
		return new BaseResponse(code, msg);
	}
}
