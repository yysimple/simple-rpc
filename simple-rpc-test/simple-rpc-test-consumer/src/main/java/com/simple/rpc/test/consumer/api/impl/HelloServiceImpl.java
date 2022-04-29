package com.simple.rpc.test.consumer.api.impl;

import com.simple.rpc.test.consumer.api.HelloService;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-04-29 11:05
 **/
public class HelloServiceImpl implements HelloService {

    @Override
    public String hiTim(String name) {
        return "hi " + name;
    }
}
