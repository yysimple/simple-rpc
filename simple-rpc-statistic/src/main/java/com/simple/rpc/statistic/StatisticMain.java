package com.simple.rpc.statistic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-06-18 18:13
 **/
@SpringBootApplication
@Configuration
public class StatisticMain {

    public static void main(String[] args) {
        SpringApplication.run(StatisticMain.class, args);
    }
}
