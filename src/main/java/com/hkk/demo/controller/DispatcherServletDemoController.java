package com.hkk.demo.controller;

/**
 * DispatcherServlet学习demo
 *
 * @author hukangkang
 * @since 2021/6/16
 */

import java.util.Objects;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.FlashMap;

@Controller
@RequestMapping("/dispatcher-servlet")
public class DispatcherServletDemoController {

    @RequestMapping("/submit")
    public String submit() {
        ((FlashMap) ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
            .getAttribute(DispatcherServlet.OUTPUT_FLASH_MAP_ATTRIBUTE))
            .put("name", "张三");
        return "redirect:showOrder";
    }

    @ResponseBody
    @RequestMapping("/showOrder")
    public String showOrder(Model model) {
        return Objects.requireNonNull(model.getAttribute("name")).toString();
    }

}
