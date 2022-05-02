package com.simple.rpc.test.spring.provider.controller;

import com.simple.rpc.test.common.spring.SpringConsumerService;
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
 * @create: 2022-05-03 00:42
 **/
@RestController
@RequestMapping("/hello")
public class ConsumerController {

    @Resource
    SpringConsumerService service;

    @GetMapping("/consumer")
    public String consumer(){
        return service.helloConsumer();
    }
}

