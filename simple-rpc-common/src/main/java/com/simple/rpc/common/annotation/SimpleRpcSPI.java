package com.simple.rpc.common.annotation;

import com.simple.rpc.common.constant.CommonConstant;

import java.lang.annotation.*;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: spi，使用该注解的类表示是一个扩展类
 *
 * @author: WuChengXing
 * @create: 2022-05-07 15:28
 **/
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SimpleRpcSPI {

    /**
     * 默认扩展类全路径
     *
     * @return 默认不填是 default
     */
    String value() default CommonConstant.DEFAULT;
}
