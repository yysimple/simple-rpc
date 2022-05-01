package com.simple.rpc.test.spring.provider.impl;

import com.simple.rpc.test.common.spring.SpringHelloService;
import org.springframework.stereotype.Service;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-05-01 11:12
 **/
@Service("springHelloService")
public class SpringHelloServiceImpl implements SpringHelloService {

    @Override
    public String helloSpring() {
        return "hello spring simple rpc!!!";
    }
}
