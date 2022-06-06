package com.hkk.demo.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;
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
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        OBJECT_MAPPER.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        OBJECT_MAPPER.setSerializerFactory(OBJECT_MAPPER.getSerializerFactory()
            .withSerializerModifier(new MyBeanSerializerModifier()));
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

    public static <T> T readValue(InputStream inputStream, TypeReference<T> typeReference)
        throws IOException {
        return OBJECT_MAPPER.readValue(inputStream, typeReference);
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

    public static class MyBeanSerializerModifier extends BeanSerializerModifier {

        private final JsonSerializer<Object> nullArrayJsonSerializer = new JsonSerializer<Object>() {
            @Override
            public void serialize(Object value, JsonGenerator gen, SerializerProvider provider)
                throws IOException {
                if (value == null) {
                    gen.writeStartArray();
                    gen.writeEndArray();
                } else {
                    gen.writeObject(value);
                }
            }
        };

        private final JsonSerializer<Object> nullStringJsonSerializer = new JsonSerializer<Object>() {
            @Override
            public void serialize(Object value, JsonGenerator gen, SerializerProvider provider)
                throws IOException {
                if (value == null) {
                    gen.writeString("");
                } else {
                    gen.writeObject(value);
                }
            }
        };

        @Override
        public List<BeanPropertyWriter> changeProperties(SerializationConfig config,
            BeanDescription beanDesc,
            List<BeanPropertyWriter> beanProperties) {
            // 循环所有的beanPropertyWriter
            for (BeanPropertyWriter writer : beanProperties) {
                // 判断字段的类型，如果是array，list，set则注册nullSerializer
                if (shouldSetDefaultValue(writer)) {
                    if (isCollectionType(writer)) {
                        //给writer注册一个自己的nullSerializer
                        writer.assignNullSerializer(nullArrayJsonSerializer);
                    } else if (isString(writer)) {
                        //给writer注册一个自己的nullSerializer
                        writer.assignNullSerializer(nullStringJsonSerializer);
                    }
                }
            }
            return beanProperties;
        }

        // 判断是什么类型
        protected boolean isCollectionType(BeanPropertyWriter writer) {
            JavaType javaType = writer.getType();
            return javaType.isCollectionLikeType();
        }

        protected boolean isString(BeanPropertyWriter writer) {
            JavaType javaType = writer.getType();
            return javaType.isTypeOrSubTypeOf(String.class);
        }

        protected boolean shouldSetDefaultValue(BeanPropertyWriter writer) {
            return writer.getAnnotation(SetDefaultValue.class) != null;
        }
    }


    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface SetDefaultValue {

    }

}
