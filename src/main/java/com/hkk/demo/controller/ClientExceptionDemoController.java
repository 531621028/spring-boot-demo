package com.hkk.demo.controller;

import com.hkk.demo.exception.ClientExceptionHandlerGroup;
import com.hkk.demo.service.ExceptionDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO
 *
 * @author hukangkang
 * @since 2021/7/16
 */
@RestController
@ClientExceptionHandlerGroup
@RequestMapping("/exception")
public class ClientExceptionDemoController {

    @Autowired
    ExceptionDemoService exceptionDemoService;

    @RequestMapping("/client")
    public String client() {
        exceptionDemoService.handler();
        return "ok";
    }

}
