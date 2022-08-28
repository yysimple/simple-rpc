package com.simple.rpc.test.starter.consumer.controller;

import com.simple.rpc.common.annotation.SimpleRpcReference;
import com.simple.rpc.test.common.starter.service.StarterHelloService;
import com.simple.rpc.test.starter.consumer.impl.ConsumerInnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-05-04 21:36
 **/
@RestController
@RequestMapping("/starter/consumer")
public class StarterConsumerController {

    @SimpleRpcReference
    private StarterHelloService service;

    @Resource
    private ConsumerInnerService innerService;

    @GetMapping("/info")
    public String hello() {
        return service.helloStarter();
    }

    @GetMapping("/noE")
    public String noE(String name) {
        String s = innerService.hasArgNoE(name);
        String s1 = innerService.simpleInvoke();
        return "远程：" + s + ", 内部：" + s1;
    }

    @GetMapping("/hasE")
    public String hasE(String name) {
        String s = innerService.hasArgHasE(name);
        String s1 = innerService.simpleInvoke();
        return "远程：" + s + ", 内部：" + s1;
    }

    @GetMapping("/ud")
    public String ud() {
        return innerService.upAndShutdown();
    }
}
