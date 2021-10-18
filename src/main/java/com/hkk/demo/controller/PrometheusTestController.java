package com.hkk.demo.controller;

import com.hkk.demo.monitor.PrometheusCustomMonitor;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 自定义指标监控测试controller
 *
 * @author hukangkang
 * @since 2021/10/15
 */
@RestController
public class PrometheusTestController {

    @Autowired
    private PrometheusCustomMonitor monitor;

    @RequestMapping("/order")
    public String order(@RequestParam(defaultValue = "0") String flag) throws Exception {
        // 统计下单次数
        monitor.getRequestCount().increment();
        if ("1".equals(flag)) {
            throw new Exception("出错啦");
        }
        long startTime = System.currentTimeMillis();
        try {
            Thread.sleep(RandomUtils.nextInt(100, 1000));
        } finally {
            long endTime = System.currentTimeMillis();
            monitor.getRequestTime().record(endTime - startTime, TimeUnit.MILLISECONDS);
        }

        Random random = new Random();
        int amount = random.nextInt(100);
        return "下单成功, 金额: " + amount;
    }
}
