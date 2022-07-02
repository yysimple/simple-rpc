package com.simple.statistic.entity.statistic;

import lombok.Data;

import java.util.List;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-07-02 00:21
 **/
@Data
public class StatisticTraceLog {

    /**
     * 用于前端展示
     */
    private String showKey;

    /**
     * 调用次数
     */
    private Long requestNum;

    /**
     * 慢接口数量
     */
    private Long showApiNum;

    /**
     * 异常请求数
     */
    private Long exceptionNum;

    /**
     * 接口数量
     */
    private String showApiNumPercentage;
}
