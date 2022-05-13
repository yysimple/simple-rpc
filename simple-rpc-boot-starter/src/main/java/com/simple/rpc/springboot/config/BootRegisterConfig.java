package com.simple.rpc.springboot.config;

import com.simple.rpc.common.config.RegistryConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 注册中心
 *
 * @author: WuChengXing
 * @create: 2022-05-04 17:28
 **/
@Configuration
@ConfigurationProperties(prefix = "simple.rpc.register")
public class BootRegisterConfig extends RegistryConfig {
}
