package com.zpwan.appcommon.config;

import com.google.common.collect.Maps;
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
 * 消息配置Util
 *
 * @author 吴爱军
 */
@Component
public class BusinessMessageConfigUtil implements ApplicationContextAware {

    @Autowired
    private static ApplicationContext applicationContext = null;

    private static final Map<Integer, String> MESSAGE_MAP = Maps.newHashMap();

    public static String getMessage(int code) {
        if (MESSAGE_MAP.isEmpty()) {
            BusinessMessageConfig businessMessageConfig = applicationContext.getBean(BusinessMessageConfig.class);
            Field[] fields = BusinessMessageConfig.class.getDeclaredFields();
            if (fields != null && fields.length > 0) {
                for (Field field : fields) {
                    if (Map.class.equals(field.getType())) {
                        ReflectionUtils.makeAccessible(field);
                        Map<Integer, String> config = (Map<Integer, String>) ReflectionUtils.getField(field, businessMessageConfig);

                        MESSAGE_MAP.putAll(config);
                    }
                }
            }
        }
        String message = MESSAGE_MAP.isEmpty() ? StringUtils.EMPTY : MESSAGE_MAP.get(code);
        return StringUtils.isNotEmpty(message) ? message : StringUtils.EMPTY;
    }

    @Override
    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext) {
        LoggerFactory.getLogger(BusinessMessageConfigUtil.class).error("setApplicationContext 被调用");
        if (BusinessMessageConfigUtil.applicationContext == null) {
            BusinessMessageConfigUtil.applicationContext = applicationContext;
        }
    }
}

