package com.simple.statistic.entity.statistic;

import lombok.Data;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 接口调用数量top
 *
 * @author: WuChengXing
 * @create: 2022-07-02 10:06
 **/
@Data
public class ApiInvokeTop {

    private String apiName;

    private String invokeNum;
}
