package com.hkk.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 工厂bean循环依赖的问题
 *
 * @author hukangkang
 * @since 2021/9/27
 */
@Slf4j
@Component
@CacheConfig(cacheNames = "cacheTest")
public class CacheCycleServiceTest {

    // @Lazy
    @Autowired
    private CacheCycleServiceTest cacheCycleServiceTest;

    @Cacheable
    @Transactional
    public void asyncTest() {
        log.info("asyncTest");
    }
}
