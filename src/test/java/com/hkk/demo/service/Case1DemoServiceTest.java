package com.hkk.demo.service;

import org.junit.jupiter.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 单元测试测试类
 *
 * @author hukangkang
 * @since 2021/10/27
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class Case1DemoServiceTest {

    @Autowired
    private TestCase1DemoService case1DemoService;

    @Test
    public void testTest() {
        Assertions.assertEquals("case1", case1DemoService.test());
    }
}
