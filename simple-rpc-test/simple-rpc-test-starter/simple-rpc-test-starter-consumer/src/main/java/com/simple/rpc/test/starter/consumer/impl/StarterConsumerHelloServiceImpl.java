package com.simple.rpc.test.starter.consumer.impl;

import com.simple.rpc.core.annotation.SimpleRpcService;
import com.simple.rpc.test.common.starter.service.StarterConsumerHelloService;
import com.simple.rpc.test.common.starter.service.StarterHelloService;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-05-04 21:32
 **/
@SimpleRpcService
public class StarterConsumerHelloServiceImpl implements StarterConsumerHelloService {

    @Override
    public String starterConsumer() {
        return "我是消费端提供的服务！！";
    }
}
