package com.chinahuik.KubernetesManager;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
@ComponentScan(basePackages = { "com.chinahuik.KubernetesManager" })
@MapperScan(basePackages = { "com.chinahuik.KubernetesManager.dao" })
public class KubernetesManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(KubernetesManagerApplication.class, args);
	}

}
