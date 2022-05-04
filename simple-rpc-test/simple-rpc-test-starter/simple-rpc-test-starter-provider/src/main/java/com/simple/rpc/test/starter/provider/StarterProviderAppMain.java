package com.simple.rpc.test.starter.provider;

import com.simple.rpc.springboot.annotaton.SimpleRpcScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 服务端启动类
 *
 * @author: WuChengXing
 * @create: 2022-05-04 21:09
 **/
@SpringBootApplication
@Configuration
@SimpleRpcScan(basePackages = {"com.simple.rpc"})
public class StarterProviderAppMain {
    public static void main(String[] args) {
        SpringApplication.run(StarterProviderAppMain.class, args);
    }
}
