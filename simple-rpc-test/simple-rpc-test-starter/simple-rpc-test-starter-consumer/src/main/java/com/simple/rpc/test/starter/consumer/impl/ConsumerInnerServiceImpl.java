package com.simple.rpc.test.starter.consumer.impl;

import com.simple.rpc.common.annotation.SimpleRpcReference;
import com.simple.rpc.test.common.starter.service.StartAndShutdownService;
import com.simple.rpc.test.common.starter.service.StarterHelloService;
import com.simple.rpc.test.common.starter.service.StarterProviderService;
import org.springframework.stereotype.Service;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-06-26 12:40
 **/
@Service
public class ConsumerInnerServiceImpl implements ConsumerInnerService {

    @SimpleRpcReference
    private StarterProviderService providerService;

    @SimpleRpcReference
    private StartAndShutdownService startAndShutdownService;

    @Override
    public String hasArgNoE(String name) {
        String s = providerService.p1(name);
        return "调用远程，返回：" + s;
    }

    @Override
    public String hasArgHasE(String name) {
        String s = providerService.e1(name);
        return "调用远程，出现异常：" + s;
    }

    @Override
    public String simpleInvoke() {
        consumerInner();
        return "我是消费端自己内部的普通调用！";
    }

    @Override
    public String upAndShutdown() {
        return startAndShutdownService.delayRequest();
    }

    private String consumerInner() {
        return "consumer-inner!";
    }
}
