package com.hkk.demo.exception;

import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author hukangkang
 * @since 2021/7/16
 */
@Order(-1)
@RestControllerAdvice(annotations = {X5ExceptionHandler.class})
public class X5GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public String handler() {
        return "x5";
    }

}
