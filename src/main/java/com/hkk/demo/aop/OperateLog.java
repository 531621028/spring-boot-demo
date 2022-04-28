package com.hkk.demo.aop;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import com.hkk.demo.aop.OperateLog.OperateLogs;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author kang
 * @date 2022/4/21
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(value = OperateLogs.class)
public @interface OperateLog {

    /**
     * 操作对象id列表
     *
     * @return 操作对象id列表
     */
    String[] objectId();

    /**
     * 操作对象类型
     *
     * @return 操作对象类型
     */
    String objectType();

    /**
     * 操作类型
     *
     * @return 操作类型
     */
    String operateType();

    /**
     * 操作详情
     *
     * @return 操作详情
     */
    String operateDetail();

    @Target({METHOD})
    @Retention(RUNTIME)
    @Documented
    @interface OperateLogs {

        OperateLog[] value();
    }
}
