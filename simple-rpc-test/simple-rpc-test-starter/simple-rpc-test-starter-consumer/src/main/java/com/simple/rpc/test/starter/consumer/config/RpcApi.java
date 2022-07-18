package com.simple.rpc.test.starter.consumer.config;

import com.simple.rpc.common.annotation.SimpleRpcReference;
import com.simple.rpc.test.common.starter.service.StarterHelloService;
import org.springframework.stereotype.Component;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-07-18 20:53
 **/
@Component
public class RpcApi {

    @SimpleRpcReference
    private StarterHelloService service;
}
