package com.hkk.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hkk.demo.domain.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<User> {

}