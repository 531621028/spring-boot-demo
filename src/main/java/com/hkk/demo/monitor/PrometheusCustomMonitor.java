package com.hkk.demo.monitor;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;

/**
 * 自定义指标监控
 *
 * @author hukangkang
 * @since 2021/10/15
 */
@Component
public class PrometheusCustomMonitor {

    private final MeterRegistry registry;
    /**
     * 记录请求次数和响应码
     */
    private Counter requestCount;

    private Timer requestTime;

    public PrometheusCustomMonitor(MeterRegistry registry) {
        this.registry = registry;
    }

    @PostConstruct
    private void init() {
        // Counter类型指标的名称时推荐使用_total作为后缀，不然会默认添加total字段io.micrometer.prometheus.PrometheusNamingConvention.name
        requestCount = registry.counter("third_part_request_status_total", "status", "statusCode");
        requestTime = registry.timer("third_part_request_time", "method", "methodName");
    }

    public Counter getRequestCount() {
        return requestCount;
    }

    public Timer getRequestTime() {
        return requestTime;
    }
}
