package com.hkk.demo.utils;

import com.fasterxml.jackson.annotation.JacksonAnnotation;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.util.VersionUtil;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.fasterxml.jackson.databind.type.MapType;
import com.google.common.collect.Lists;
import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @author hukangkang
 * @date 2021-08-16
 */
@Slf4j
@UtilityClass
public class AnonymizeJsonUtil {

    private static final String EMPTY_JSON = "{}";
    private static final List<String> ANONYMIZE_NAMES = Lists.newArrayList("cash");
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        // 反序列化时忽略不存在的字段
        OBJECT_MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        // 注册处理敏感字段的扩展模块
        OBJECT_MAPPER.registerModule(new AnonymizeModule());
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


    private static class AnonymizeModule extends Module {

        private static final String MODULE_NAME = "jackson-anonymize-module";

        private final Version version = VersionUtil.parseVersion("0.0.1", "com.hkk", MODULE_NAME);

        @Override
        public String getModuleName() {
            return MODULE_NAME;
        }

        @Override
        public Version version() {
            return version;
        }

        @Override
        public void setupModule(SetupContext setupContext) {
            setupContext.addBeanSerializerModifier(new FieldAnonymizeModifier());
        }

        public static class FieldAnonymizeModifier extends BeanSerializerModifier {

            @Override
            public List<BeanPropertyWriter> changeProperties(SerializationConfig config,
                BeanDescription beanDesc, List<BeanPropertyWriter> beanProperties) {
                beanProperties = super.changeProperties(config, beanDesc, beanProperties);
                List<BeanPropertyWriter> newWriters = new ArrayList<>();
                for (BeanPropertyWriter writer : beanProperties) {
                    if (needAnonymize(writer)) {
                        // 如果带有 @Sensitive 注解，并且是字符串，则使用自定义处理
                        JsonSerializer<Object> serializer = new AnonymizeJsonSerializer(
                            writer.getSerializer());
                        writer.assignSerializer(serializer);

                    }
                    newWriters.add(writer);
                }

                return newWriters;
            }

            private boolean needAnonymize(BeanPropertyWriter writer) {
                return writer.getAnnotation(Anonymize.class) != null || ANONYMIZE_NAMES.stream()
                    .anyMatch(s -> StringUtils.containsIgnoreCase(s, writer.getName()));
            }

            @Override
            public JsonSerializer<?> modifyMapSerializer(SerializationConfig config,
                MapType valueType,
                BeanDescription beanDesc, JsonSerializer<?> serializer) {
                return super.modifyMapSerializer(config, valueType, beanDesc, serializer);
            }

        }

        private static class AnonymizeJsonSerializer extends JsonSerializer<Object> {

            private final JsonSerializer<Object> serializer;

            public AnonymizeJsonSerializer(JsonSerializer<Object> serializer) {
                this.serializer = serializer;
            }

            @Override
            public void serialize(Object value, JsonGenerator jsonGenerator,
                SerializerProvider serializerProvider) throws IOException {
                if (value != null && canAnonymize(value)) {
                    value = processSensitiveField(value.toString());
                } else if (value != null) {
                    throw new RuntimeException("匿名化只支持字符串和数字类型");
                }
                if (this.serializer == null) {
                    serializerProvider.defaultSerializeValue(value, jsonGenerator);
                } else {
                    this.serializer.serialize(value, jsonGenerator, serializerProvider);
                }
            }

            private boolean canAnonymize(Object value) {
                return value instanceof String
                    || value instanceof Number
                    || value.getClass().isPrimitive();
            }

            private static String processSensitiveField(String input) {
                if (StringUtils.isBlank(input)) {
                    return input;
                }
                return "****";
            }
        }

    }

    @JacksonAnnotation
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD})
    public @interface Anonymize {

    }


    public static void main(String[] args) {
        AnonymizeData data = new AnonymizeData();
        data.setPassword("1234");
        data.setUsername("123456");
        data.setSalary(10000);
        data.setCash(32);
        System.out.println(AnonymizeJsonUtil.toJsonStringOrEmpty(data));
    }

    @Data
    public static class AnonymizeData {

        @Anonymize
        private String password;
        private String username;
        @Anonymize
        private Integer salary;
        private int cash;
    }
}
