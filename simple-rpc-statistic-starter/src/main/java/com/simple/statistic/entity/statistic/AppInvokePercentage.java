package com.simple.statistic.entity.statistic;

import lombok.Data;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 应用调用比例
 *
 * @author: WuChengXing
 * @create: 2022-07-02 10:04
 **/
@Data
public class AppInvokePercentage {

    private String appName;

    private Long invokeNum;
}
