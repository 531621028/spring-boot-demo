package com.hkk.demo.mapper;

import com.hkk.demo.domain.User;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    @Rollback
    public void testDao() {
        User user = new User();
        user.setAge(12);
        user.setName("插入测试");
        user.setEmail("插入邮箱");
        userMapper.insert(user);
        List<User> userList = userMapper.selectList(null);
        Assert.assertEquals(6, userList.size());
    }

}