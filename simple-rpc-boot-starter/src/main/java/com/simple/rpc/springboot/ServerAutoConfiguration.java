package com.simple.rpc.springboot;

import com.simple.rpc.springboot.config.BootRegisterConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-05-04 23:54
 **/
@Configuration
@EnableConfigurationProperties(BootRegisterConfig.class)
public class ServerAutoConfiguration {

    @Bean
    @ConditionalOnBean(BootRegisterConfig.class)
    public ServerInit serverInit(BootRegisterConfig bootRegisterConfig) {
        return new ServerInit(bootRegisterConfig);
    }
}
