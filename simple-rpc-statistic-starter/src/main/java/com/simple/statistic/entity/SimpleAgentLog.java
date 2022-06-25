package com.simple.statistic.entity;

import lombok.Data;

/**
* 项目: simple-rpc
*
* 功能描述: 日志链路监控表
*
* @author: yysimple
* @create: 2022-06-25 17:24:22
**/
@Data
public class SimpleAgentLog {
    /**
     * 主键
     */
    private Long id;
    /**
     * 链路的traceId
     */
    private String traceId;
    /**
     * 链路的spanId
     */
    private String spanId;
    /**
     * 链路的parentSpanId
     */
    private String parentSpanId;
    /**
     * 链路的parentSpanId
     */
    private Integer level;
    /**
     * 方法进入时间
     */
    private Long entryTime;
    /**
     * 方法退出时间
     */
    private Long exitTime;
    /**
     * 应用名
     */
    private String appName;
    /**
     * host
     */
    private String host;
    /**
     * 类名
     */
    private String clazzName;
    /**
     * 方法名
     */
    private String methodName;
    /**
     * 请求信息
     */
    private String requestInfo;
    /**
     * 返回信息
     */
    private String resultInfo;
    /**
     * 异常信息
     */
    private String exceptionInfo;

    /**
     * 调用状态：0-退出；1-进入
     */
    private Integer invokeStatus;

    /**
     * 是否是调用者
     */
    private Integer invoker;
}