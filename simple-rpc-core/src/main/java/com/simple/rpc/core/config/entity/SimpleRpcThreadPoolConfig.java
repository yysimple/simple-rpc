package com.simple.rpc.core.config.entity;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 线程池配置
 *
 * @author: WuChengXing
 * @create: 2022-04-22 22:25
 **/
public class SimpleRpcThreadPoolConfig {

    /**
     * 核心线程数
     */
    private Integer corePoolSize = 10;

    /**
     * 最大线程数
     */
    private Integer maximumPoolSize = 100;

    /**
     * 等待线程的存活时间
     */
    private Long keepAliveTime = 60L;

    /**
     * 队列中可等待线程数
     */
    private Integer queueSize = 1000;

    public Integer getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(Integer corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public Integer getMaximumPoolSize() {
        return maximumPoolSize;
    }

    public void setMaximumPoolSize(Integer maximumPoolSize) {
        this.maximumPoolSize = maximumPoolSize;
    }

    public Long getKeepAliveTime() {
        return keepAliveTime;
    }

    public void setKeepAliveTime(Long keepAliveTime) {
        this.keepAliveTime = keepAliveTime;
    }

    public Integer getQueueSize() {
        return queueSize;
    }

    public void setQueueSize(Integer queueSize) {
        this.queueSize = queueSize;
    }
}