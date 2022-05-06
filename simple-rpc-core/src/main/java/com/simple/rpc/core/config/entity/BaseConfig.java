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
     * 是否开启负载均衡策略
     */
    private Boolean loadBalance;

    /**
     * 负载均衡算法
     */
    private String rule;

    /**
     * 超时时间
     */
    private Long timeout;

    /**
     * 重试次数
     */
    private Long retryNum;

    public Boolean getLoadBalance() {
        return loadBalance;
    }

    public void setLoadBalance(Boolean loadBalance) {
        this.loadBalance = loadBalance;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public Long getTimeout() {
        return timeout;
    }

    public void setTimeout(Long timeout) {
        this.timeout = timeout;
    }

    public Long getRetryNum() {
        return retryNum;
    }

    public void setRetryNum(Long retryNum) {
        this.retryNum = retryNum;
    }

    @Override
    public String toString() {
        return "BaseConfig{" +
                "loadBalance=" + loadBalance +
                ", rule='" + rule + '\'' +
                ", timeout=" + timeout +
                ", retryNum=" + retryNum +
                '}';
    }
}
