package com.simple.rpc.test.starter.consumer.controller;

import com.simple.rpc.core.annotation.SimpleRpcReference;
import com.simple.rpc.test.common.starter.service.StarterConsumerHelloService;
import com.simple.rpc.test.common.starter.service.StarterHelloService;
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
 * @create: 2022-05-04 21:36
 **/
@RestController
@RequestMapping("/starter/consumer")
public class StarterConsumerController {

    @SimpleRpcReference
    private StarterHelloService service;

    @GetMapping("/info")
    public String hello() {
        return service.helloStarter();
    }
}
