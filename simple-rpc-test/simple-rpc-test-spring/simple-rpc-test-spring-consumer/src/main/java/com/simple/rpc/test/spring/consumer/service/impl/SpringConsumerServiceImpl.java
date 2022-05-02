package com.simple.rpc.test.spring.consumer.service.impl;

import com.simple.rpc.test.common.spring.SpringConsumerService;
import org.springframework.stereotype.Service;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-05-02 22:59
 **/
@Service("springConsumerService")
public class SpringConsumerServiceImpl implements SpringConsumerService {
    @Override
    public String helloConsumer() {
        return "我即是一个消费者，也是提供者！！";
    }
}
