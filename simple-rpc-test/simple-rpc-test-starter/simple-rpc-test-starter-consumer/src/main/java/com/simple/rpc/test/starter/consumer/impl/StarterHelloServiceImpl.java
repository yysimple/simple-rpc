package com.simple.rpc.test.starter.consumer.impl;

import com.simple.rpc.core.annotation.SimpleRpcService;
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
public class StarterHelloServiceImpl implements StarterHelloService {

    @Override
    public String helloStarter() {
        return "simple rpc Provider Service";
    }
}
