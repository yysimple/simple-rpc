package com.simple.statistic.entity.response;

import lombok.Data;

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
    private Long traceNum;

    /**
     *
     */
    private Long showApiNum;
}
