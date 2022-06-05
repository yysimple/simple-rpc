package com.simple.rpc.test.starter.provider.two;

import com.simple.rpc.springboot.annotaton.SimpleRpcScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-05-26 21:01
 **/
@SpringBootApplication
@Configuration
@SimpleRpcScan(basePackages = {"com.simple.rpc"})
public class ProviderDeepApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProviderDeepApplication.class, args);
    }
}
