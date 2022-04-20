package com.simple.rpc.core.annotation;

import org.springframework.boot.autoconfigure.web.ServerProperties;
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
 * 功能描述: 开启RPC的注解
 *
 * @author: WuChengXing
 * @create: 2022-04-19 19:27
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@EnableConfigurationProperties(ServerProperties.class)
@ComponentScan("com.simple.rpc.*")
public @interface EnableSimpleRpc {
}
