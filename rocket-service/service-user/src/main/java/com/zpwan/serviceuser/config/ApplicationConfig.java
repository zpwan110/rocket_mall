package com.zpwan.serviceuser.config;

import com.google.common.collect.Maps;
import com.zpwan.appcommon.config.BusinessMessageConfig;
import com.zpwan.appcommon.config.BusinessMessageConfigUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * @program: rocket
 * @description:
 * @author: hzzp
 * @create: 2019-03-15 19:04
 **/
public class ApplicationConfig {
    @Autowired
    private static ApplicationContext applicationContext = null;

    private static final Map<Integer, String> MESSAGE_MAP = Maps.newHashMap();

}
