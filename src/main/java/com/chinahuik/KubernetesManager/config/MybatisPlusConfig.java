/**
 *
 */
package com.chinahuik.KubernetesManager.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.extension.parsers.BlockAttackSqlParser;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.chinahuik.KubernetesManager.handler.MyMetaObjectHandler;

/**
 * @author open-source@chinahuik.com
 *
 */
@Configuration
public class MybatisPlusConfig {
	/**
	 * 自动填充
	 */
	@Bean
	public MetaObjectHandler metaObjectHandler() {
		return new MyMetaObjectHandler();
	}

	/**
	 * 分页
	 */
	@Bean
	public PaginationInterceptor paginationInterceptor() {
		final PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
		final List<ISqlParser> sqlParserList = new ArrayList<>();
		// 攻击 SQL 阻断解析器、加入解析链
		sqlParserList.add(new BlockAttackSqlParser());
		paginationInterceptor.setSqlParserList(sqlParserList);
		return paginationInterceptor;
	}

}
