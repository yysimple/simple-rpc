package com.simple.rpc.core.config.entity;

import com.simple.rpc.core.annotation.SimpleRpcConfig;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: rpc的一些基础配置，全局配置等
 *
 * @author: WuChengXing
 * @create: 2022-05-06 16:34
 **/
@SimpleRpcConfig(prefix = "simple.rpc.base")
public class BaseConfig {

    /**
     * 负载均衡算法
     */
    private String loadBalanceRule;

    /**
     * 超时时间
     */
    private Long timeout;

    /**
     * 重试次数
     */
    private Integer retryNum;

    /**
     * 序列化的SPI类型
     */
    private String serializer;

    /**
     * 压缩的SPI类型
     */
    private String compressor;

    /**
     * 注册中心的SPI类型
     */
    private String register;

    public String getLoadBalanceRule() {
        return loadBalanceRule;
    }

    public void setLoadBalanceRule(String loadBalanceRule) {
        this.loadBalanceRule = loadBalanceRule;
    }

    public Long getTimeout() {
        return timeout;
    }

    public void setTimeout(Long timeout) {
        this.timeout = timeout;
    }

    public Integer getRetryNum() {
        return retryNum;
    }

    public void setRetryNum(Integer retryNum) {
        this.retryNum = retryNum;
    }

    public String getSerializer() {
        return serializer;
    }

    public void setSerializer(String serializer) {
        this.serializer = serializer;
    }

    public String getCompressor() {
        return compressor;
    }

    public void setCompressor(String compressor) {
        this.compressor = compressor;
    }

    public String getRegister() {
        return register;
    }

    public void setRegister(String register) {
        this.register = register;
    }

    @Override
    public String toString() {
        return "BaseConfig{" +
                "loadBalanceRule='" + loadBalanceRule + '\'' +
                ", timeout=" + timeout +
                ", retryNum=" + retryNum +
                ", serializer='" + serializer + '\'' +
                ", compressor='" + compressor + '\'' +
                ", register='" + register + '\'' +
                '}';
    }
}
