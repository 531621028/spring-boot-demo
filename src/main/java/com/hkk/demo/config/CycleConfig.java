package com.hkk.demo.config;

import com.hkk.demo.service.CycleServiceTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * factoryBean 循环依赖
 *
 * @author hukangkang
 * @since 2021/9/27
 */
@Component("cycleConfig")
public class CycleConfig {

    @Autowired
    private CycleServiceTest cycleServiceTest;

}
