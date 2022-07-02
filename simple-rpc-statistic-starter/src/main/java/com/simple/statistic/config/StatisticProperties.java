package com.simple.statistic.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 配置类
 *
 * @author: WuChengXing
 * @create: 2022-07-02 09:46
 **/
@Configuration
@ConfigurationProperties(prefix = "statistic")
@Data
public class StatisticProperties {

    /**
     * 满接口阈值
     */
    private Long slowTime;
}
