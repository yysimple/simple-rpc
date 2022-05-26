package com.simple.rpc.test.starter.provider.controller;

import com.simple.rpc.common.annotation.SimpleRpcReference;
import com.simple.rpc.test.common.starter.service.ProviderTwoService;
import com.simple.rpc.test.common.starter.service.StarterConsumerHelloService;
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
 * @create: 2022-05-04 21:42
 **/
@RestController
@RequestMapping("/starter/provider")
public class StarterProviderController {

    @SimpleRpcReference
    private StarterConsumerHelloService consumerHelloService;

    @SimpleRpcReference
    private ProviderTwoService providerTwoService;

    @GetMapping("/info")
    public String hello() {
        return consumerHelloService.starterConsumer();
    }

    @GetMapping("/info/two")
    public String helloTwo() {
        return providerTwoService.helloProviderTwo("1 调用 2");
    }

    @GetMapping("/info/noArgs")
    public String helloTwoNoArgs() {
        return providerTwoService.helloProviderTwoNoArgs();
    }

}
