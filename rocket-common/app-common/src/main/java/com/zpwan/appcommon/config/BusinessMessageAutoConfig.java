package com.zpwan.appcommon.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 通用BeansConfig
 *
 * @author 吴爱军
 */
@Configuration
public class BusinessMessageAutoConfig {

    @Bean
    public BusinessMessageConfig businessMessageConfig() {
        return new BusinessMessageConfig();
    }

    @Bean
    public BusinessMessageConfigUtil messageConfigUtil() {
        return new BusinessMessageConfigUtil();
    }
}
