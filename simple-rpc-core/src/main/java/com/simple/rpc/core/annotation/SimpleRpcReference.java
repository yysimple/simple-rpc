package com.simple.rpc.core.annotation;

import java.lang.annotation.*;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 服务注入
 *
 * @author: WuChengXing
 * @create: 2022-05-04 16:27
 **/
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SimpleRpcReference {

    /**
     * 版本，没有特殊要求不用填写
     *
     * @return 版本，默认""
     */
    String version() default "";

    /**
     * 别名
     *
     * @return
     */
    String alias() default "";
}
