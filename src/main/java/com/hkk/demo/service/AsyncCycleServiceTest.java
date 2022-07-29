package com.hkk.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 工厂bean循环依赖的问题
 *
 * @author hukangkang
 * @since 2021/9/27
 */
@Slf4j
@Component
public class AsyncCycleServiceTest {

    /**
     * 加了@Lazy之后 在调用AbstractAutowireCapableBeanFactory#populateBean填充属性的时候不会调用getEarlyBeanReferences
     * 去获取提前暴露的Bean，Spring会在被@Lazy标记的属性上放一个增强对象的占位符， 参考ContextAnnotationAutowireCandidateResolver#getLazyResolutionProxyIfNecessary
     * 这个增强对象在调用对应方法的时候实时去BeanFactory中获取实际的对象来执行方法
     * https://blog.csdn.net/f641385712/article/details/93620967
     */
    @Lazy
    @Autowired
    private AsyncCycleServiceTest asyncCycleServiceTest;

    @Async
    public void asyncTest() {
        log.info("asyncTest");
    }
}
