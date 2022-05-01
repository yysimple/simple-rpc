package com.simple.rpc.test.spring.consumer.controller;

import com.simple.rpc.test.common.spring.SpringHelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-05-01 16:27
 **/
@RestController
@RequestMapping("/hello")
public class HelloServiceController {

    @Autowired
    SpringHelloService springHelloService;

    @GetMapping("/info")
    public String hello() {
        return springHelloService.helloSpring();
    }
}
