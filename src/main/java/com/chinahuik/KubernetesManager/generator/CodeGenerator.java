/**
 *
 */
package com.chinahuik.KubernetesManager.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

/**
 * @author open-source@chinahuik.com
 *
 */
public class CodeGenerator {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// 代码生成器
		final AutoGenerator mpg = new AutoGenerator();
		// 全局配置
		final GlobalConfig gc = buildGlobalConfig();
		mpg.setGlobalConfig(gc);
		// 数据源配置
		final DataSourceConfig dsc = buildDataSourceConfig();
		mpg.setDataSource(dsc);
		// 包配置
		final PackageConfig pc = buildPackageConfig();
		mpg.setPackageInfo(pc);
		final InjectionConfig cfg = buildInjectionConfig(pc);
		mpg.setCfg(cfg);
		// 配置模板
		final TemplateConfig templateConfig = buildTemplateConfig();
		mpg.setTemplate(templateConfig);

		// 策略配置
		final StrategyConfig strategy = buildStrategyConfig();
		mpg.setStrategy(strategy);
		mpg.setTemplateEngine(new FreemarkerTemplateEngine());
		try {
			mpg.execute();
		} catch (final Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * <p>
	 * 读取控制台内容
	 * </p>
	 */
	@SuppressWarnings("resource")
	public static String scanner(String tip) {
		final Scanner scanner = new Scanner(System.in);
		final StringBuilder help = new StringBuilder();
		help.append("请输入" + tip + "：");
		System.out.println(help.toString());
		if (scanner.hasNext()) {
			final String ipt = scanner.next();
			if (StringUtils.isNotEmpty(ipt)) {
				return ipt;
			}
		}
		throw new MybatisPlusException("请输入正确的" + tip + "！");
	}

	private static DataSourceConfig buildDataSourceConfig() {
		final DataSourceConfig dsc = new DataSourceConfig();
		dsc.setUrl(
				"jdbc:mysql://localhost:3306/kubecloud?useUnicode=true&characterEncoding=utf-8&useSSL=false");
		// dsc.setSchemaName("public");
		dsc.setDriverName("com.mysql.cj.jdbc.Driver");
		dsc.setUsername("root");
		dsc.setPassword("123456");
		System.out.println(dsc);
		return dsc;
	}

	private static GlobalConfig buildGlobalConfig() {
		final GlobalConfig gc = new GlobalConfig();
		final String projectPath = System.getProperty("user.dir");
		gc.setOutputDir(projectPath + "/src/main/java");
		gc.setAuthor("open-source@chinahuik.com");
		gc.setOpen(false);
		gc.setSwagger2(true); // 实体属性 Swagger2 注解
		gc.setFileOverride(true);// 是否覆盖文件
		gc.setActiveRecord(false);// 开启 activeRecord 模式
		gc.setEnableCache(false);// XML 二级缓存
		gc.setBaseResultMap(true);// XML ResultMap
		gc.setBaseColumnList(true);// XML columList
		// gc.setKotlin(true); 是否生成 kotlin 代码
		// 自定义文件命名，注意 %s 会自动填充表实体属性！
		// gc.setEntityName("%sModel");
		gc.setMapperName("%sDao");
		gc.setXmlName("%sMapper");
		gc.setServiceName("%sService");
		gc.setServiceImplName("%sServiceImpl");
		gc.setControllerName("%sController");
		System.out.println(gc);
		return gc;
	}

	private static InjectionConfig buildInjectionConfig(final PackageConfig pc) {
		final String projectPath = System.getProperty("user.dir");
		// 自定义配置
		final InjectionConfig cfg = new InjectionConfig() {
			@Override
			public void initMap() {
				// to do nothing
			}
		};

		// 如果模板引擎是 freemarker
		final String templatePath = "/templates/mapper.xml.ftl";
		// 如果模板引擎是 velocity
		// String templatePath = "/templates/mapper.xml.vm";

		// 自定义输出配置
		final List<FileOutConfig> focList = new ArrayList<>();
		// 自定义配置会被优先输出
		focList.add(new FileOutConfig(templatePath) {
			@Override
			public String outputFile(TableInfo tableInfo) {
				// 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
				return projectPath + "/src/main/resources/mybatis/mapper/"
						+ pc.getModuleName() + "/" + tableInfo.getXmlName() + ".xml";
			}
		});
		/*
		 * cfg.setFileCreate(new IFileCreate() {
		 *
		 * @Override public boolean isCreate(ConfigBuilder configBuilder, FileType
		 * fileType, String filePath) { // 判断自定义文件夹是否需要创建 checkDir("调用默认方法创建的目录");
		 * return false; } });
		 */
		cfg.setFileOutConfigList(focList);
		return cfg;
	}

	private static PackageConfig buildPackageConfig() {
		final PackageConfig pc = new PackageConfig();
		pc.setModuleName("KubernetesManager");
		pc.setParent("com.chinahuik");
		pc.setEntity("model");
		pc.setMapper("dao");
		pc.setService("service");
		pc.setServiceImpl("service.impl");
		pc.setXml("mapper");
		pc.setController("controller");
		System.out.println(pc);
		return pc;
	}

	private static StrategyConfig buildStrategyConfig() {
		// 策略配置
		final StrategyConfig strategy = new StrategyConfig();
		// strategy.setTablePrefix(new String[] { "fy_" });
		strategy.setNaming(NamingStrategy.underline_to_camel);
		strategy.setColumnNaming(NamingStrategy.underline_to_camel);
		strategy.setSuperEntityClass("com.chinahuik.KubernetesManager.bean.BaseModel");
		// 公共父类
		// strategy.setSuperControllerClass("com.chinahuik.wechat.web.BaseController");
		// 自定义 mapper 父类
		// strategy.setSuperMapperClass("com.chinahuik.wechat.dao.BaseMapper");
		strategy.setEntityLombokModel(true);
		strategy.setRestControllerStyle(true);
		strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
		strategy.setControllerMappingHyphenStyle(true);
		// 写于父类中的公共字段, 自定义实体，公共字段
		strategy.setSuperEntityColumns(new String[] { "id", "created_by", "create_time",
				"updated_by", "update_time" });
		System.out.println(strategy);
		return strategy;
	}

	private static TemplateConfig buildTemplateConfig() {
		final TemplateConfig templateConfig = new TemplateConfig();

		// 配置自定义输出模板
		// 指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
		// templateConfig.setEntity("templates/entity2.java");
		// templateConfig.setService();
		templateConfig.setController("templates/controller.java");
		templateConfig.setEntity("templates/entity.java");
		templateConfig.setXml(null);
		return templateConfig;
	}
}
