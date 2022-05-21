package com.simple.rpc.common.config;

import com.simple.rpc.common.annotation.SimpleRpcConfig;

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
     * 权重算法的时候的权重值
     */
    private Integer weights;

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

    /**
     * 心跳间隔时间
     */
    private Long beatIntervalTime;

    /**
     * 客户端停止心跳或者写操作后，服务端断开时间
     */
    private Long stopConnectTime;

    /**
     * 服务端的port配置
     */
    private Integer port;

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

    public Integer getWeights() {
        return weights;
    }

    public void setWeights(Integer weights) {
        this.weights = weights;
    }

    public Long getBeatIntervalTime() {
        return beatIntervalTime;
    }

    public void setBeatIntervalTime(Long beatIntervalTime) {
        this.beatIntervalTime = beatIntervalTime;
    }

    public Long getStopConnectTime() {
        return stopConnectTime;
    }

    public void setStopConnectTime(Long stopConnectTime) {
        this.stopConnectTime = stopConnectTime;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "BaseConfig{" +
                "loadBalanceRule='" + loadBalanceRule + '\'' +
                ", weights=" + weights +
                ", timeout=" + timeout +
                ", retryNum=" + retryNum +
                ", serializer='" + serializer + '\'' +
                ", compressor='" + compressor + '\'' +
                ", register='" + register + '\'' +
                ", beatIntervalTime=" + beatIntervalTime +
                ", stopConnectTime=" + stopConnectTime +
                ", port=" + port +
                '}';
    }
}
