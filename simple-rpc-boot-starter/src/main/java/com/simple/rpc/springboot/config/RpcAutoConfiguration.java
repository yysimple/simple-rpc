package com.simple.rpc.springboot.config;

import com.simple.rpc.springboot.ApplicationClosedEventListener;
import com.simple.rpc.springboot.ServerInitBeanPostProcessor;
import com.simple.rpc.springboot.ServiceBeanPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextClosedEvent;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: rpc的配置类
 *
 * @author: WuChengXing
 * @create: 2022-07-24 21:36
 **/
@Configuration
@ConditionalOnProperty(value = "simple.pagination.enabled", matchIfMissing = true)
@EnableConfigurationProperties({BootBaseConfig.class, BootRegisterConfig.class})
public class RpcAutoConfiguration {

    @Bean("serverInitBeanPostProcessor")
    public BeanPostProcessor serverInitBeanPostProcessor() {
        return new ServerInitBeanPostProcessor();
    }

    @Bean("serviceBeanPostProcessor")
    @ConditionalOnClass(ServerInitBeanPostProcessor.class)
    public BeanPostProcessor serviceBeanPostProcessor() {
        return new ServiceBeanPostProcessor();
    }

    @Bean("applicationClosedEventListener")
    public ApplicationListener<ContextClosedEvent> applicationClosedEventListener() {
        return new ApplicationClosedEventListener();
    }

}
