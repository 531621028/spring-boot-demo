package com.hkk.demo.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 缓存测试类
 *
 * @author hukangkang
 * @since 2021/10/27
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class CacheManagerDemoControllerTest {

    @Autowired
    private CacheManagerDemoController cacheManagerDemoController;

    @Test
    public void testGet30s() {
        Assertions.assertEquals("30s", cacheManagerDemoController.get30s());
    }

}