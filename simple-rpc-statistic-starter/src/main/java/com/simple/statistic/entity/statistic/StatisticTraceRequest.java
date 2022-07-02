package com.simple.statistic.entity.statistic;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-07-02 00:11
 **/
@Data
public class StatisticTraceRequest {

    /**
     * 应用名
     */
    private String appName;

    /**
     * 接口名
     */
    private String apiName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date endTime;
}
