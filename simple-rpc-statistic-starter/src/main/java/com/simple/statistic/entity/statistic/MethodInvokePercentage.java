package com.simple.statistic.entity.statistic;

import lombok.Data;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 方法调用比例
 *
 * @author: WuChengXing
 * @create: 2022-07-02 10:05
 **/
@Data
public class MethodInvokePercentage {

    private String appName;

    private String methodUseNum;
}
