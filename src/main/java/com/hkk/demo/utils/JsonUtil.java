package com.hkk.demo.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

/**
 * @author hukangkang
 * @date 2021-08-16
 */
@Slf4j
@UtilityClass
public class JsonUtil {

    private static final String EMPTY_JSON = "{}";
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    /**
     * @param jsonStr json的字符串
     * @param clazz   类型对象
     * @param <T>     参数化类型
     * @return 反序列化的对象
     */
    public static <T> Optional<T> readValueOption(String jsonStr, Class<T> clazz) {
        try {
            return Optional.of(OBJECT_MAPPER.readValue(jsonStr, clazz));
        } catch (JsonProcessingException e) {
            log.error("jsonString to object failed, json string is {}", jsonStr, e);
        }
        return Optional.empty();
    }

    /**
     * @param jsonStr json的字符串
     * @param clazz   类型对象
     * @param <T>     参数化类型
     * @return 反序列化的对象
     * @throws JsonProcessingException jsonStr有问题是抛出异常
     */
    public static <T> T readValue(String jsonStr, Class<T> clazz) throws JsonProcessingException {
        return OBJECT_MAPPER.readValue(jsonStr, clazz);
    }


    /**
     * @param jsonStr       json的字符串
     * @param typeReference 类型参数
     * @param <T>           参数化类型
     * @return 反序列化的对象
     */
    public static <T> Optional<T> readValueOption(String jsonStr, TypeReference<T> typeReference) {
        try {
            return Optional.of(readValue(jsonStr, typeReference));
        } catch (JsonProcessingException e) {
            log.error("jsonString to object failed, json string is {}", jsonStr, e);
        }
        return Optional.empty();
    }

    /**
     * @param jsonStr       json的字符串
     * @param typeReference 类型参数
     * @param <T>           参数化类型
     * @return 反序列化的对象
     * @throws JsonProcessingException jsonStr有问题是抛出异常
     */
    public static <T> T readValue(String jsonStr, TypeReference<T> typeReference)
        throws JsonProcessingException {
        return OBJECT_MAPPER.readValue(jsonStr, typeReference);
    }

    /**
     * 把java对象转换为json字符串
     *
     * @param object x
     * @return 序列化之后的字符串
     * @throws JsonProcessingException 序列化报错
     */
    public static String toJsonString(Object object) throws JsonProcessingException {
        return OBJECT_MAPPER.writeValueAsString(object);
    }

    /**
     * 把java对象转换为json字符串
     *
     * @param object 需要转换的对象
     * @return 如果报错返回空字符串
     */
    public static String toJsonStringOrEmpty(Object object) {
        try {
            return toJsonString(object);
        } catch (JsonProcessingException e) {
            log.error("json to string error", e);
        }
        return EMPTY_JSON;
    }
}
