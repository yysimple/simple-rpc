package com.simple.statistic.entity;

import lombok.Data;

import java.util.Date;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-06-26 02:07
 **/
@Data
public class AgentLogView {

    /**
     * 调用id
     */
    private String traceId;

    /**
     * 方法调用深度
     */
    private Integer invokeDeep;

    /**
     * 调用方法数量
     */
    private Integer invokeMethodNum;

    /**
     * 是否存在异常
     */
    private Integer existException;

    private Date invokeTime;
}
