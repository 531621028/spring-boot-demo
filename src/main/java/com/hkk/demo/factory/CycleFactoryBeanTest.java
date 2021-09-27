package com.hkk.demo.factory;

import com.hkk.demo.config.CycleConfig;
import com.hkk.demo.service.CycleServiceTest;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 工厂bean循环依赖
 *
 * @author hukangkang
 * @since 2021/9/27
 */
@Component
public class CycleFactoryBeanTest implements FactoryBean<CycleServiceTest> {

    public CycleFactoryBeanTest() {
        System.out.println(this.getClass().getSimpleName() + " init");
    }

    @Autowired
    private CycleConfig cycleConfig;

    @Override
    public CycleServiceTest getObject() throws Exception {
        if (cycleConfig == null) {
            throw new IllegalStateException();
        }
        return new CycleServiceTest();
    }

    @Override
    public Class<?> getObjectType() {
        return CycleServiceTest.class;
    }
}
