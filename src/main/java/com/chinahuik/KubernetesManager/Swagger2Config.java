/**
 *
 */
package com.chinahuik.KubernetesManager;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author open-source@chinahuik.com
 *
 */
@Configuration
@EnableSwagger2
@ComponentScan("com.chinahuik.KubernetesManager.controller")
public class Swagger2Config {
	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
				.apis(RequestHandlerSelectors
						.basePackage("com.chinahuik.KubernetesManager.controller"))
				.paths(PathSelectors.any()).build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("数据操作的API接口").description("数据操作的API接口")
				.termsOfServiceUrl("http://www.chinahuik.com/")
				.contact(new Contact("open-source@chinahuik.com",
						"http://www.chinahuik.com", "open-source@chinahuik.com"))
				.version("1.0").build();
	}
}
