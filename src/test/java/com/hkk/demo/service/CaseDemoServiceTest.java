package com.hkk.demo.service;

import org.junit.jupiter.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CaseDemoServiceTest {

    @Autowired
    private TestCaseDemoService caseDemoService;

    @Test
    public void testTest() {
        Assertions.assertEquals("case", caseDemoService.test());
    }
}
