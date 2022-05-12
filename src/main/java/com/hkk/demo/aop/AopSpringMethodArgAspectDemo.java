package com.hkk.demo.aop;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * spring EL在aop使用的aspect
 *
 * @author hukangkang
 * @since 2021/10/11
 */
@Slf4j
@Aspect
// @Component
public class AopSpringMethodArgAspectDemo {

    /**
     * org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator#wrapIfNecessary()
     * org.springframework.aop.framework.autoproxy.AbstractAdvisorAutoProxyCreator#getAdvicesAndAdvisorsForBean()
     * org.springframework.aop.framework.autoproxy.AbstractAdvisorAutoProxyCreator#findAdvisorsThatCanApply()
     * org.springframework.aop.support.AopUtils#canApply() org.springframework.aop.aspectj.AspectJExpressionPointcut#matches()
     * !walker.testsSubtypeSensitiveVars() 针对public java.lang.Object org.springframework.security.config.annotation.configuration.AutowireBeanFactoryObjectPostProcessor.postProcess(java.lang.Object)
     * 返回了 true，最终被识别为有效的Advisor，会进行动态代理， 而AutowireBeanFactoryObjectPostProcessor类是final的，所以在动态代理生成子类的时候报错了，或者一些其他的类在动态代理创建的过程不满足初始化的条件而报错
     *
     * 在AOP代理的时候尽量减少切面切入的风险
     * 本类的代理逻辑被封装下面这个类里面org.springframework.aop.aspectj.annotation.InstantiationModelAwarePointcutAdvisorImpl
     */

    @Pointcut("@args(com.hkk.demo.aop.MethodArg)")
    public void pointcut() {

    }

    @Before("pointcut()")
    public void doBefore(JoinPoint joinPoint) {
        Object[] params = joinPoint.getArgs();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Annotation[][] annotations = method.getParameterAnnotations();
        for (int i = 0; i < annotations.length; i++) {
            Object param = params[i];
            Annotation[] paramAnn = annotations[i];
            //参数为空，直接下一个参数
            if (param == null || paramAnn.length == 0) {
                continue;
            }
            for (Annotation annotation : paramAnn) {
                //这里判断当前注解是否为Test.class
                if (annotation.annotationType().equals(MethodArg.class)) {
                    System.out.println("parameter name is " + param);
                    break;
                }
            }
        }
    }

}
