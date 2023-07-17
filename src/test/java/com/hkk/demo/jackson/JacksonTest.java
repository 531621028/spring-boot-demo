package com.hkk.demo.jackson;

import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.containsString;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

/**
 * @author kang
 * @date 2023/6/27
 */
public class JacksonTest {

    @Test
    public void whenSerializingUsingJsonGetter_thenCorrect()
        throws JsonProcessingException {

        MyBean bean = new MyBean(1, "My bean");

        String result = new ObjectMapper().writeValueAsString(bean);
        System.out.println(result);

        assertThat(result, containsString("My bean"));
        assertThat(result, containsString("1"));
    }
}
