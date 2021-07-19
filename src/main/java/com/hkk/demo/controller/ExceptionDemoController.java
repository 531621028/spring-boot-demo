package com.hkk.demo.controller;

import com.hkk.demo.service.ExceptionDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO
 *
 * @author hukangkang
 * @since 2021/7/16
 */
@RestController
@RequestMapping(value = "/exception")
@Order(0)
public class ExceptionDemoController {

    @Autowired
    ExceptionDemoService exceptionDemoService;

    @RequestMapping("/common")
    public String test() {
        exceptionDemoService.handler();
        return "common";
    }

}
