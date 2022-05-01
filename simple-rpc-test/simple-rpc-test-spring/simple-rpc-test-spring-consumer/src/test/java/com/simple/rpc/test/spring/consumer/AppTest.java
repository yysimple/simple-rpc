package com.simple.rpc.test.spring.consumer;

import com.simple.rpc.test.common.spring.SpringHelloService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-05-01 11:21
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-config.xml")
public class AppTest {

    @Resource
    private SpringHelloService springHelloService;

    @Test
    public void test(){
        springHelloService.helloSpring();
        System.out.println();
    }
}
