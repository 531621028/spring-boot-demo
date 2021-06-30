package com.hkk.demo.controller;

import com.hkk.demo.service.CacheDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 缓存处理的测试
 *
 * @author hukangkang
 * @since 2021/6/30
 */
@RestController
@RequestMapping("cache")
public class CacheManagerDemoController {

    @Autowired
    private CacheDemoService cacheDemoService;

    @RequestMapping("get30s")
    public String get30s() {
        return cacheDemoService.get30s();
    }

    @RequestMapping("get60s")
    public String get60s() {
        return cacheDemoService.get60s();
    }

}
