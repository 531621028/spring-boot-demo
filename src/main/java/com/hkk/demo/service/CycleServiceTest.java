package com.hkk.demo.service;

import com.hkk.demo.factory.CycleFactoryBeanTest;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 工厂bean循环依赖的问题
 *
 * @author hukangkang
 * @since 2021/9/27
 */
public class CycleServiceTest {

    public CycleServiceTest() {
        System.out.println(this.getClass().getSimpleName() + " init");
    }

    @Autowired
    private CycleFactoryBeanTest cycleFactoryBeanTest;
}
