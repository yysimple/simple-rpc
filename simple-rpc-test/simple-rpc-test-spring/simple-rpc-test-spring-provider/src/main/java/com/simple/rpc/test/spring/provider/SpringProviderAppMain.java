package com.simple.rpc.test.spring.provider;

import com.simple.rpc.spring.annotation.EnableSimpleRpc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-05-01 11:12
 **/
@SpringBootApplication
@Configuration
@EnableSimpleRpc
@ImportResource(locations = {"classpath:spring-config.xml"})
public class SpringProviderAppMain {
    public static void main(String[] args) {
        SpringApplication.run(SpringProviderAppMain.class, args);
    }
}
