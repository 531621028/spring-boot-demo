package com.hkk.demo.service;

import org.springframework.stereotype.Service;

/**
 * TODO
 *
 * @author hukangkang
 * @since 2021/7/16
 */
@Service
public class ExceptionDemoService {

    public void handler(){
        throw new RuntimeException();
    }

}
