package com.simple.rpc.interfaces.impl;

import com.simple.rpc.api.HelloService;
import com.simple.rpc.api.domain.Hi;
import org.springframework.stereotype.Service;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-04-22 09:46
 **/
@Service("helloService")
public class HelloServiceImpl implements HelloService {
    @Override
    public String hi() {
        return "hi bugstack rpc";
    }

    @Override
    public String say(String str) {
        return str;
    }

    @Override
    public String sayHi(Hi hi) {
        return hi.getUserName() + " say：" + hi.getSayMsg();
    }
}
