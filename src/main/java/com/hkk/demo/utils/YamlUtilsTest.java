package com.hkk.demo.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.hkk.demo.utils.YamlUtilsTest.ReportFields.TestFields;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

/**
 * @author kang
 * @date 2022/3/22
 */
public class YamlUtilsTest {

    public static final String fields = "fields";
    public static final String fieldCode = "fieldCode";
    public static final String fieldDesc = "fieldDesc";

    public static List<Map<String, Object>> getDictJson() throws IOException {
        InputStream inputStream = YamlUtilsTest.class.getResourceAsStream("/dict.json");
        return JsonUtil.readValue(inputStream, new TypeReference<List<Map<String, Object>>>() {

        });
    }

    public static void main(String[] args) throws IOException {

        List<Map<String, Object>> allMap = getDictJson();
        Map<Integer, List<TestFields>> report = new LinkedHashMap<>();
        allMap.forEach(moduleMap -> {
            List<TestFields> modules = report.computeIfAbsent(1, key -> new ArrayList<>());
            TestFields module = new TestFields();
            module.setFieldCode((String) moduleMap.get(fieldCode));
            module.setFieldDesc((String) moduleMap.get(fieldDesc));
            module.setFieldType("1");
            Map<String, String> fieldMap = (Map<String, String>) moduleMap.get(fields);
            List<TestFields> fields = new ArrayList<>();
            fieldMap.forEach((key, value) -> {
                TestFields field = new TestFields();
                field.setFieldCode(key);
                field.setFieldDesc(value);
                field.setFieldType("2");
                field.setNeedDecrypt(false);
                fields.add(field);
            });
            module.setFields(fields);
            modules.add(module);

        });
        //转储选项设置
        DumperOptions options = new DumperOptions();
        //通常使用的yaml格式
        Map<String,Object> map = JsonUtil.readValue(JsonUtil.toJsonString(report),
            new TypeReference<Map<String, Object>>() {
            });
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        //标量格式
        options.setDefaultScalarStyle(DumperOptions.ScalarStyle.PLAIN);

        Yaml yaml = new Yaml(options);
        String str = yaml.dump(map);
        System.out.println(str);
    }


    /**
     * @author kang
     * @date 2022/3/17
     */
    public static class ReportFields {

        private Map<Integer, List<TestFields>> fields;

        public Map<Integer, List<TestFields>> getFields() {
            return fields;
        }

        public void setFields(
            Map<Integer, List<TestFields>> fields) {
            this.fields = fields;
        }

        public static class TestFields {

            /**
             * 字段的Code
             */
            private String fieldCode;
            /**
             * 字段的描述
             */
            private String fieldDesc;
            /**
             * 1:模块 2:字段
             */
            private String fieldType;
            /**
             * 是否需要解密
             */
            private boolean needDecrypt;
            /**
             * 字段集合
             */
            private List<TestFields> fields;

            public String getFieldCode() {
                return fieldCode;
            }

            public void setFieldCode(String fieldCode) {
                this.fieldCode = fieldCode;
            }

            public String getFieldDesc() {
                return fieldDesc;
            }

            public void setFieldDesc(String fieldDesc) {
                this.fieldDesc = fieldDesc;
            }

            public String getFieldType() {
                return fieldType;
            }

            public void setFieldType(String fieldType) {
                this.fieldType = fieldType;
            }

            public boolean isNeedDecrypt() {
                return needDecrypt;
            }

            public void setNeedDecrypt(boolean needDecrypt) {
                this.needDecrypt = needDecrypt;
            }

            public List<TestFields> getFields() {
                return fields;
            }

            public void setFields(List<TestFields> fields) {
                this.fields = fields;
            }
        }
    }
}
