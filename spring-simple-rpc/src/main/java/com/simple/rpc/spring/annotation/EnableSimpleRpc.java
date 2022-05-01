package com.simple.rpc.spring.annotation;

import org.springframework.context.annotation.ComponentScan;

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
@ComponentScan("com.simple.rpc.*")
public @interface EnableSimpleRpc {

}
