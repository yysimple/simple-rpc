package com.simple.rpc.test.starter.provider.impl;

import com.simple.rpc.common.annotation.SimpleRpcReference;
import com.simple.rpc.common.annotation.SimpleRpcService;
import com.simple.rpc.test.common.starter.service.ProviderTwoService;
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

    @SimpleRpcReference
    ProviderTwoService providerTwoService;

    @Override
    public String helloStarter() {
        String s = providerTwoService.helloProviderTwo("我是第一个提供者！");
        // String s = providerTwoService.helloProviderTwoNoArgs();
        // int i = 1 / 0;
        return "调用下一级方法：" + s;
//        return "provider!!";
    }
}
