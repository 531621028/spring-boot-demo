package com.hkk.demo.controller;

import com.hkk.demo.exception.ServerExceptionHandlerGroup;
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
@ServerExceptionHandlerGroup
@RequestMapping("/exception")
public class ServerExceptionDemoController {

    @Autowired
    ExceptionDemoService exceptionDemoService;

    @RequestMapping("/server")
    public String server() {
        exceptionDemoService.handler();
        return "ok";
    }

}
