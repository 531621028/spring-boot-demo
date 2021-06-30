package com.hkk.demo.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author hukangkang
 */
@RestController
@CrossOrigin(maxAge = 72000L)
public class CrossOriginsDemoController {

    @GetMapping("/")
    public Mono<String> hello() {
        return Mono.just("Hello World!");
    }

    @GetMapping(value = "/phone", params = {"send", "test"})
    public String send() {
        return "send";
    }

    @GetMapping(value = "/phone", params = "verify")
    public String verify() {
        return "verify";
    }

}
