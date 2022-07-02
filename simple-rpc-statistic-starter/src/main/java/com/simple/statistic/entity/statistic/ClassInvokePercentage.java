package com.simple.statistic.entity.statistic;

import lombok.Data;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 类调用比例
 *
 * @author: WuChengXing
 * @create: 2022-07-02 10:04
 **/
@Data
public class ClassInvokePercentage {

    /**
     * 应用名
     */
    private String appName;

    /**
     * 类调用次数
     */
    private Long clazzUseNum;
}
