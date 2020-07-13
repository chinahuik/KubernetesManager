/**
 *
 */
package com.chinahuik.KubernetesManager.handler;

import java.util.Date;

import org.apache.ibatis.reflection.MetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

/**
 * @author open-source@chinahuik.com
 *
 */
public class MyMetaObjectHandler implements MetaObjectHandler {
	private static final Logger logger = LoggerFactory
			.getLogger(MyMetaObjectHandler.class);

	@Override
	public void insertFill(MetaObject metaObject) {
		logger.debug("新增方法实体填充");
		// this.setFieldValByName("id",
		// UUID.randomUUID().toString().replace("-", ""), metaObject);
		this.setFieldValByName("createdBy", "admin", metaObject);
		this.setFieldValByName("createTime", new Date(), metaObject);
		this.setFieldValByName("updatedBy", "admin", metaObject);
		this.setFieldValByName("updateTime", new Date(), metaObject);
	}

	@Override
	public void updateFill(MetaObject metaObject) {
		logger.debug("更新方法实体填充");
		this.setFieldValByName("updatedBy", "admin", metaObject);
		this.setFieldValByName("updateTime", new Date(), metaObject);
	}

}
