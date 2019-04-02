package com.zpwan.serviceuser;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

/**
 * @author zhangpeng
 */
@SpringBootApplication(scanBasePackages ="com.zpwan.serviceuser")
@ComponentScan(basePackageClasses = {ServiceUserApplication.class})
@MapperScan("com.zpwan.serviceuser.dao")
public class ServiceUserApplication{
	public static void main(String[] args) {
		SpringApplication.run(ServiceUserApplication.class, args);
	}

}
