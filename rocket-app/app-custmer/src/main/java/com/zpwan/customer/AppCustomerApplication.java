package com.zpwan.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

/**
 * @author zhangpeng
 */
@SpringBootApplication
@ComponentScan(basePackageClasses = {AppCustomerApplication.class})
@EnableFeignClients(basePackages = {"com.zpwan.customer.client"})
@EnableEurekaClient
public class AppCustomerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppCustomerApplication.class, args);
	}
	@Autowired
	private RestTemplateBuilder builder;

	// 使用RestTemplateBuilder来实例化RestTemplate对象，spring默认已经注入了RestTemplateBuilder实例
	@Bean
public RestTemplate restTemplate() {
	return builder.build();
}
}
