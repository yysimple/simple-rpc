package com.simple.rpc.test.provider;

import com.simple.rpc.core.annotation.EnableSimpleRpc;
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
 * @create: 2022-04-29 11:15
 **/
@SpringBootApplication
@Configuration
@EnableSimpleRpc
@ImportResource(locations = {"classpath:spring-config.xml"})
public class ProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProviderApplication.class, args);
    }
}
