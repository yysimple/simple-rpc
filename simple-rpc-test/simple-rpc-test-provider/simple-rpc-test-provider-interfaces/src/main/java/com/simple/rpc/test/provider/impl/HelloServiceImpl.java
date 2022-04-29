package com.simple.rpc.test.provider.impl;

import com.simple.rpc.test.provider.HelloService;
import com.simple.rpc.test.provider.domain.Hi;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-04-29 11:16
 **/
public class HelloServiceImpl implements HelloService {
    @Override
    public String hi() {
        return "hello simple rpc";
    }

    @Override
    public String say(String str) {
        return str;
    }

    @Override
    public String sayHi(Hi hi) {
        return "hi: " + hi;
    }
}
