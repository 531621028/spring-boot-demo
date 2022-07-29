package com.hkk.demo.infrastructure;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.support.AbstractCacheManager;

/**
 * 自定义缓存管理器
 *
 * @author hukangkang
 * @since 2021/6/28
 */
public class DemoMultiCacheManager extends AbstractCacheManager {

    private final CacheManager localCacheManger;
    private final CacheManager remoteCacheManager;

    public DemoMultiCacheManager(CacheManager localCacheManger, CacheManager remoteCacheManager) {
        this.localCacheManger = localCacheManger;
        this.remoteCacheManager = remoteCacheManager;
    }

    @Override
    public Cache getCache(String name) {
        if (name.startsWith("local")) {
            return localCacheManger.getCache(name);
        } else {
            return remoteCacheManager.getCache(name);
        }
    }

    @Override
    public void initializeCaches() {
        if (localCacheManger instanceof AbstractCacheManager) {
            ((AbstractCacheManager) localCacheManger).initializeCaches();
        }
        if (remoteCacheManager instanceof AbstractCacheManager) {
            ((AbstractCacheManager) remoteCacheManager).initializeCaches();
        }
    }

    @Override
    protected Collection<? extends Cache> loadCaches() {
        Set<String> localCacheNames = new HashSet<>(localCacheManger.getCacheNames());
        Set<String> remoteCacheNames = new HashSet<>(remoteCacheManager.getCacheNames());
        Collection<Cache> caches = new ArrayList<>();
        localCacheNames.forEach(name -> caches.add(localCacheManger.getCache(name)));

        remoteCacheNames.forEach(name -> {
            if (!localCacheNames.contains(name)) {
                caches.add(remoteCacheManager.getCache(name));
            }
        });
        return caches;
    }
}
