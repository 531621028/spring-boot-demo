package com.hkk.demo.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author hukangkang
 * @since 2021/7/16
 */
@RestControllerAdvice(annotations = {ServerExceptionHandlerGroup.class})
public class ServerExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public String handler() {
        return "server";
    }

}
