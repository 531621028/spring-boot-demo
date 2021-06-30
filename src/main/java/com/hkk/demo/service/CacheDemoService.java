package com.hkk.demo.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * 缓存测试
 *
 * @author hukangkang
 * @since 2021/6/30
 */
@Service
public class CacheDemoService {

    @Cacheable(cacheNames = "CacheDemo60s#${spring.cache.ttl.CacheDemo60s}")
    public String get60s() {
        return "60s";
    }

    @Cacheable(cacheNames = "CacheDemo30s#${spring.cache.ttl.CacheDemo30s}")
    public String get30s() {
        return "30s";
    }

}
