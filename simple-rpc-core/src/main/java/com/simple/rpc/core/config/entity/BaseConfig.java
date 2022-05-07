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

    @Override
    public String toString() {
        return "BaseConfig{" +
                "loadBalanceRule='" + loadBalanceRule + '\'' +
                ", timeout=" + timeout +
                ", retryNum=" + retryNum +
                '}';
    }
}
