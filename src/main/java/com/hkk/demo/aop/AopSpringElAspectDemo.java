package com.hkk.demo.aop;

import com.hkk.demo.annotation.AntiDuplicateSubmit;
import com.hkk.demo.utils.JsonUtil;
import java.lang.reflect.Method;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Component;

/**
 * spring EL在aop使用的aspect
 *
 * @author hukangkang
 * @since 2021/10/11
 */
@Slf4j
@Aspect
@Component
public class AopSpringElAspectDemo {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Pointcut("@annotation(com.hkk.demo.annotation.AntiDuplicateSubmit) || @within(com.hkk.demo.annotation.AntiDuplicateSubmit)")
    public void pointcut() {

    }

    @Before("pointcut()")
    public void doBefore(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        AntiDuplicateSubmit antiDuplicateSubmit = method.getAnnotation(AntiDuplicateSubmit.class);
        String value;
        if (StringUtils.isNotBlank(antiDuplicateSubmit.key())) {
            SpringElDemoEvaluationContext evaluationContext = new SpringElDemoEvaluationContext(new Object(), method, joinPoint.getArgs(),
                new DefaultParameterNameDiscoverer());
            ExpressionParser parser = new SpelExpressionParser();
            Expression expression = parser.parseExpression(antiDuplicateSubmit.key());
            value = JsonUtil.toJsonStringOrEmpty(expression.getValue(evaluationContext, Object.class));
        } else {
            value = JsonUtil.toJsonStringOrEmpty(JsonUtil.toJsonStringOrEmpty(joinPoint.getArgs()));
        }
        log.info("submit value is {}", value);
    }

}
