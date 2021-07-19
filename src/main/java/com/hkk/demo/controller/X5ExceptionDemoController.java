package com.hkk.demo.controller;

import com.hkk.demo.exception.X5ExceptionHandler;
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
@X5ExceptionHandler
@RequestMapping("/exception")
public class X5ExceptionDemoController {

    @Autowired
    ExceptionDemoService exceptionDemoService;


    @RequestMapping("/x5")
    public String testX5() {
        exceptionDemoService.handler();
        return "x5";
    }

}
