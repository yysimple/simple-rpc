package com.simple.rpc.test.starter.consumer;

import com.simple.rpc.springboot.annotaton.SimpleRpcScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 消费者启动器
 *
 * @author: WuChengXing
 * @create: 2022-05-04 20:50
 **/
@SpringBootApplication
@Configuration
@SimpleRpcScan(basePackages = {"com.simple.rpc"})
public class StarterConsumerAppMain {

    public static void main(String[] args) {
        SpringApplication.run(StarterConsumerAppMain.class, args);
    }
}
