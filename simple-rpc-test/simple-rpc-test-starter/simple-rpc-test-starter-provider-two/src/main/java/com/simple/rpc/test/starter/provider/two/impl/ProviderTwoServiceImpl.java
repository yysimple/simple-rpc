package com.simple.rpc.test.starter.provider.two.impl;

import com.simple.rpc.common.annotation.SimpleRpcService;
import com.simple.rpc.test.common.starter.service.ProviderTwoService;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-05-26 21:27
 **/
@SimpleRpcService
public class ProviderTwoServiceImpl implements ProviderTwoService {

    @Override
    public String helloProviderTwo(String msg) {
        String s = innerProviderTwo();
        return "我是第二个提供者，接收到的消息：" + s + "--" + msg;
    }

    @Override
    public String helloProviderTwoNoArgs() {
        return "我是第二个提供者，没有参数的";
    }

    private String innerProviderTwo() {
        return "inner invoke!!";
    }
}
