package com.simple.rpc.test.starter.provider.impl;

import com.simple.rpc.core.annotation.SimpleRpcService;
import com.simple.rpc.test.common.starter.service.StarterHelloService;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-05-04 21:39
 **/
@SimpleRpcService
public class StarterHelloServiceImpl implements StarterHelloService {

    @Override
    public String helloStarter() {
        return "simple rpc consumer provider!!";
    }
}
