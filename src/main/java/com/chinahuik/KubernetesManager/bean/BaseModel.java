/**
 *
 */
package com.chinahuik.KubernetesManager.bean;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
import lombok.ToString;

/**
 * @author open-source@chinahuik.com
 *
 */
@Data
@ToString
public class BaseModel {
	@TableId(value = "id", type = IdType.AUTO)
	@TableField(fill = FieldFill.INSERT)
	private Integer id;

	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Date updateTime;

	@TableField(fill = FieldFill.INSERT)
	private String createdBy;

	@TableField(fill = FieldFill.INSERT_UPDATE)
	private String updatedBy;

}
