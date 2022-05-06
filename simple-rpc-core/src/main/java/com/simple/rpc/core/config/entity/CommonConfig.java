package com.simple.rpc.core.config.entity;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 公共配置
 *
 * @author: WuChengXing
 * @create: 2022-05-06 20:05
 **/
public class CommonConfig {

    private RegistryConfig registryConfig;

    private BaseConfig baseConfig;

    private ConsumerConfig consumerConfig;

    public RegistryConfig getRegistryConfig() {
        return registryConfig;
    }

    public void setRegistryConfig(RegistryConfig registryConfig) {
        this.registryConfig = registryConfig;
    }

    public BaseConfig getBaseConfig() {
        return baseConfig;
    }

    public void setBaseConfig(BaseConfig baseConfig) {
        this.baseConfig = baseConfig;
    }

    public ConsumerConfig getConsumerConfig() {
        return consumerConfig;
    }

    public void setConsumerConfig(ConsumerConfig consumerConfig) {
        this.consumerConfig = consumerConfig;
    }
}
