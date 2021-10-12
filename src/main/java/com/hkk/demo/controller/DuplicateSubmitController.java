package com.hkk.demo.controller;

import com.hkk.demo.annotation.AntiDuplicateSubmit;
import com.hkk.demo.req.DuplicateSubmit;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 重复提交的接口
 *
 * @author hukangkang
 * @since 2021/10/12
 */
@RestController
@RequestMapping("/duplicate")
public class DuplicateSubmitController {

    @RequestMapping("/submit")
    @AntiDuplicateSubmit()
    public String submit(@RequestBody DuplicateSubmit duplicateSubmit) {
        return duplicateSubmit.getData();
    }

}
