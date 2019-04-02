package com.zpwan.ssoserver;

import com.zpwan.ssoserver.config.WebSecurityConfig;
import com.zpwan.ssoserver.filter.JwtAuthenticationEntryPoint;
import com.zpwan.ssoserver.filter.JwtAuthenticationTokenFilter;
import com.zpwan.ssoserver.filter.RestAuthenticationAccessDeniedHandler;
import com.zpwan.ssoserver.service.CustomUserDetailsService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.client.RestTemplate;

/**
 * @author zhangpeng
 */
@SpringBootApplication(scanBasePackageClasses = SsoServerApplication.class)
@EnableFeignClients(basePackages = {"com.zpwan.ssoserver.client"})
@ComponentScan("com.zpwan.ssoserver.client")
public class SsoServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SsoServerApplication.class, args);
	}

	@Bean
	public WebSecurityConfig restTemplate(){
		return new WebSecurityConfig(new JwtAuthenticationEntryPoint(), new RestAuthenticationAccessDeniedHandler(),new CustomUserDetailsService(),new JwtAuthenticationTokenFilter());
	}
}
