package com.zpwan.appcommon.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zpwan.appcommon.exception.JsonException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;


/**
 * Json Util based on Jackson
 *
 * @author wuaj
 * @version 1.0.0.0
 * @create 2016-05-30
 */
public class JsonUtils {

    /**
     * 忽略对象中值为NULL或""的属性
     */
    public static final JsonUtils EXCLUDE_EMPTY = new JsonUtils(JsonInclude.Include.NON_EMPTY);

    /**
     * 忽略对象中值为默认值的属性
     */
    public static final JsonUtils EXCLUDE_DEFAULT = new JsonUtils(JsonInclude.Include.NON_DEFAULT);

    /**
     * 默认不排除任何属性
     */
    public static final JsonUtils DEFAULT = new JsonUtils();

    private ObjectMapper mapper;

    private JsonUtils() {
        mapper = new ObjectMapper();
        // ignore attributes exists in json string, but not in java object when deserialization
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        SimpleDateFormat myFmt1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        mapper.setDateFormat(myFmt1);
    }

    private JsonUtils(JsonInclude.Include include) {
        mapper = new ObjectMapper();
        // set serialization feature
        mapper.setSerializationInclusion(include);
        // ignore attributes exists in json string, but not in java object when deserialization
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    /**
     * convert an object(POJO, Collection, ...) to json string
     *
     * @param target target object
     * @return json string
     */
    public String toJson(Object target) {
        return toJson(target, false);
    }

    public String toJson(Object target, boolean quiet) {
        try {
            return mapper.writeValueAsString(target);
        } catch (IOException e) {
            if (!quiet) {
                throw new JsonException(e);
            } else {
                e.printStackTrace();
            }
        }
        return "";
    }

    /**
     * deserialize a json to target class object
     *
     * @param json   json string
     * @param target target class
     * @param <T>    the generic type
     * @return target object
     */
    public <T> T fromJson(String json, Class<T> target) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        try {
            return mapper.readValue(json, target);
        } catch (IOException e) {
            throw new JsonException(e);
        }
    }

    /**
     * 反序列化
     *
     * @param javaType   JavaType
     * @param jsonString json string
     * @param <T>        the generic type
     * @return the javaType's object
     * @see #createCollectionType(Class, Class...)
     */
    @SuppressWarnings("unchecked")
    public <T> T fromJson(String jsonString, JavaType javaType) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }
        try {
            return mapper.readValue(jsonString, javaType);
        } catch (Exception e) {
            throw new JsonException(e);
        }
    }

    /**
     * construct collection type
     *
     * @param collectionClass collection class, such as ArrayList, HashMap, ...
     * @param elementClasses  element class
     * @return JavaType
     */
    public JavaType createCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

    public JsonUtils setDateFormat(String pattern, Locale locale) {
        SimpleDateFormat myFmt1 = new SimpleDateFormat(pattern, locale);
        mapper.setDateFormat(myFmt1);
        return this;
    }
}