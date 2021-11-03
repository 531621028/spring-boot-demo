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

    @Cacheable(cacheNames = "CacheDemo60s#${spring.cache.ttl.cacheDemo60s}")
    public String get60s() {
        return "60s";
    }

    @Cacheable(cacheNames = "localCache")
    public String localCache() {
        return "local";
    }

    @Cacheable(cacheNames = "remoteCache")
    public String remoteCache() {
        return "remote";
    }

}
