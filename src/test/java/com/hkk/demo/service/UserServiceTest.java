package com.hkk.demo.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.hkk.demo.mapper.UserMapper;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {

    @MockBean
    private UserMapper userMapper;
    @Autowired
    private UserService userService;

    @Test
    public void testModelServiceServiceImpl() {
        when(userMapper.insert(any())).thenReturn(0);
        Assertions.assertEquals(0, userService.testService());
    }

}