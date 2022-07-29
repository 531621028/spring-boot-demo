package com.hkk.demo.service;

import org.springframework.beans.factory.annotation.Lookup;
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
        currentService();
        return "60s";
    }

    @Cacheable(cacheNames = "remote")
    public String localCache() {
        return "local";
    }

    @Cacheable(cacheNames = "remoteCache")
    public String remoteCache() {
        return "remote";
    }

    @Lookup
    public CacheDemoService currentService() {
        //  @Lookup 会被Spring代理，从而获取CacheDemoService，代码的实现没有什么作用，直接返回null
        // 此方法返回的bean和注入到controller的bean是同一个，都是最终注入到Spring中的bean，
        // 和执行到上面currentService()断点的bean不是同一个，可以通过debug看出
        return null;
    }

}
