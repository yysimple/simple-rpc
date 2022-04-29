package com.simple.rpc.spring.annotation;

import com.simple.rpc.core.register.config.RegisterProperties;
import com.simple.rpc.spring.ServerAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-04-29 16:45
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({ServerAutoConfiguration.class})
@EnableConfigurationProperties(RegisterProperties.class)
@ComponentScan("com.simple.rpc.*")
public @interface EnableSimpleRpc {
}
