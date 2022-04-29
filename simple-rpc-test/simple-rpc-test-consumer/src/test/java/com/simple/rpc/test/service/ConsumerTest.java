package com.simple.rpc.test.service;

import com.simple.rpc.test.provider.HelloService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-04-29 11:24
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-config.xml")
public class ConsumerTest {
    @Resource
    private HelloService helloService;

    @Test
    public void hi() {
        String hi = helloService.hi();
        System.out.println(hi);
    }
}
