package com.hkk.demo.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.hkk.demo.utils.JsonUtil.SetDefaultValue;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

/**
 * @author hukangkang
 * @date 2021-08-16
 */
@Slf4j
@UtilityClass
public class JsonUtilTest {

    public static void main(String[] args) throws JsonProcessingException {
        System.out.println(JsonUtil.toJsonString(new TestDemo()));
    }

    @Setter
    @Getter
    public static class TestDemo {

        @SetDefaultValue
        private String name;
        private String country;
        @SetDefaultValue
        private List<String> address;
    }


}
