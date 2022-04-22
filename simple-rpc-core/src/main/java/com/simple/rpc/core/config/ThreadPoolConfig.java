package com.simple.rpc.core.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 线程池配置
 *
 * @author: WuChengXing
 * @create: 2022-04-22 22:06
 **/
@Configuration
@EnableConfigurationProperties(RpcThreadPoolProperties.class)
public class ThreadPoolConfig {

    @Bean("simpleRpcThreadPool")
    @ConditionalOnMissingBean(RpcThreadPoolProperties.class)
    public ThreadPoolExecutor threadPoolExecutor(RpcThreadPoolProperties pool) {
        return new ThreadPoolExecutor(pool.getCorePoolSize(),
                pool.getMaximumPoolSize(),
                pool.getKeepAliveTime(),
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(pool.getQueueSize()),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());
    }
}
