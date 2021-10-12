package com.hkk.demo.aop;

import java.lang.reflect.Method;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.ParameterNameDiscoverer;

/**
 * 在AOP中springEL
 *
 * @author hukangkang
 * @since 2021/10/11
 */
public class SpringElDemoEvaluationContext extends MethodBasedEvaluationContext {

    public SpringElDemoEvaluationContext(Object rootObject, Method method, Object[] arguments,
        ParameterNameDiscoverer parameterNameDiscoverer) {
        super(rootObject, method, arguments, parameterNameDiscoverer);
    }
}
