package com.hkk.demo.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.hkk.demo.exception.DemoCacheErrorHandler;
import com.hkk.demo.infrastructure.DemoMultiCacheManager;
import com.hkk.demo.infrastructure.DemoRedisCacheManager;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 * 通用配置模板
 *
 * @author hukangkang
 * @since 2021/6/25
 */
@Configuration
public class DemoConfig {

    @Bean
    public HttpMessageConverters jacksonHttpMessageConverters() {
        MappingJackson2HttpMessageConverter jacksonConverter = new MappingJackson2HttpMessageConverter();
        jacksonConverter.setObjectMapper(jsonObjectMapper());
        return new HttpMessageConverters(jacksonConverter);
    }

    @Bean
    @Primary
    public ObjectMapper jsonObjectMapper() {
        ObjectMapper jsonObjectMapper = new ObjectMapper();
        jsonObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        jsonObjectMapper.registerModule(javaTimeModule());
        return jsonObjectMapper;
    }

    public JavaTimeModule javaTimeModule() {
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern("HH:mm:ss")));
        return javaTimeModule;
    }

    @Bean
    public ObjectMapper redisObjectMapper() {
        ObjectMapper jsonObjectMapper = new ObjectMapper();
        jsonObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //这一句必须要，作用是序列化时将对象全类名一起保存下来,不然反序列化的時候会报错
        jsonObjectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
        jsonObjectMapper.registerModule(javaTimeModule());
        return jsonObjectMapper;
    }


    RedisCacheConfiguration getCacheConfigurationWithTtl(ObjectMapper redisObjectMapper, long seconds) {
        RedisSerializationContext.SerializationPair<Object> pair = RedisSerializationContext.SerializationPair
            .fromSerializer(new GenericJackson2JsonRedisSerializer(redisObjectMapper));
        return RedisCacheConfiguration
            .defaultCacheConfig()
            .entryTtl(Duration.ofSeconds(seconds))
            // 设置key为String
            .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
            // 设置value 为自动转Json的Object
            .serializeValuesWith(pair);
    }

    @Bean
    public CacheManager cacheRedisManager(RedisConnectionFactory redisConnectionFactory) {
        Map<String, RedisCacheConfiguration> initialCaches = new LinkedHashMap<>();
        RedisCacheConfiguration defaultCacheConfig = getCacheConfigurationWithTtl(redisObjectMapper(), 600);
        return new DemoRedisCacheManager(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory), defaultCacheConfig,
            initialCaches, true);
    }

    @Bean
    public CacheManager cacheCaffeineManager() {
        Caffeine<Object, Object> caffeine = Caffeine.newBuilder().expireAfterWrite(10, TimeUnit.MINUTES);
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        caffeineCacheManager.setCaffeine(caffeine);
        return caffeineCacheManager;
    }

    @Bean
    @Primary
    public CacheManager cacheManager(CacheManager cacheCaffeineManager, CacheManager cacheRedisManager) {
        return new DemoMultiCacheManager(cacheCaffeineManager, cacheRedisManager);
    }

    @Bean
    public CachingConfigurer cachingConfigurer() {
        return new CachingConfigurer() {
            @Override
            public CacheManager cacheManager() {
                return null;
            }

            @Override
            public CacheResolver cacheResolver() {
                return null;
            }

            @Override
            public KeyGenerator keyGenerator() {
                return null;
            }

            @Override
            public CacheErrorHandler errorHandler() {
                return new DemoCacheErrorHandler();
            }
        };
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer(redisObjectMapper()));
        redisTemplate.setDefaultSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
        return redisTemplate;
    }

}
