package com.simple.rpc.springboot.annotaton;

import com.simple.rpc.springboot.scanner.SimpleRpcScannerRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: rpc类扫描
 *
 * @author: WuChengXing
 * @create: 2022-05-04 17:00
 **/
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({SimpleRpcScannerRegistrar.class})
public @interface SimpleRpcScan {

    /**
     * 基础扫描包
     *
     * @return
     */
    String[] basePackages();
}
