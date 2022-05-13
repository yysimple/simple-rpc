package com.simple.rpc.springboot.config;

import com.simple.rpc.common.config.BaseConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-05-06 18:50
 **/
@Configuration
@ConfigurationProperties(prefix = "simple.rpc.base")
public class BootBaseConfig extends BaseConfig {
}
