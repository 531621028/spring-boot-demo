package com.hkk.demo.infrastructure;

import java.time.Duration;
import java.util.Map;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.util.StringUtils;

/**
 * 自定义缓存管理器
 *
 * @author hukangkang
 * @since 2021/6/28
 */
public class DemoRedisCacheManager extends RedisCacheManager implements EnvironmentAware {

    private Environment environment;

    public DemoRedisCacheManager(RedisCacheWriter cacheWriter,
        RedisCacheConfiguration defaultCacheConfiguration,
        Map<String, RedisCacheConfiguration> initialCacheConfigurations, boolean allowInFlightCacheCreation) {
        super(cacheWriter, defaultCacheConfiguration, initialCacheConfigurations, allowInFlightCacheCreation);
    }

    @Override
    protected RedisCache createRedisCache(String name, RedisCacheConfiguration cacheConfig) {
        String[] array = StringUtils.delimitedListToStringArray(name, "#");
        name = array[0];
        // 解析TTL
        if (array.length > 1) {
            String ttlStr = environment.resolveRequiredPlaceholders(array[1]);
            long ttl = Long.parseLong(ttlStr);
            cacheConfig = cacheConfig.entryTtl(Duration.ofSeconds(ttl));
        }
        return super.createRedisCache(name, cacheConfig);
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
