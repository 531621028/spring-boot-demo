package com.hkk.demo.service;

import com.hkk.demo.domain.User;
import com.hkk.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户服务类
 *
 * @author hukangkang
 * @since 2021/10/27
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public int testService() {
        User user = new User();
        user.setAge(12);
        user.setName("插入测试");
        user.setEmail("插入邮箱");
        return userMapper.insert(user);
    }

}
