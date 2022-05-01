package com.simple.rpc.test.spring.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-05-01 11:14
 **/
@SpringBootApplication
@ImportResource(locations = {"classpath:spring-config.xml"})
public class SpringConsumerAppMain {
    public static void main(String[] args) {
        SpringApplication.run(SpringConsumerAppMain.class, args);
    }
}
