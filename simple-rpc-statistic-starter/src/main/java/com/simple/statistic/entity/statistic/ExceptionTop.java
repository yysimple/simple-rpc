package com.simple.statistic.entity.statistic;

import lombok.Data;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 异常调用top
 *
 * @author: WuChengXing
 * @create: 2022-07-02 10:05
 **/
@Data
public class ExceptionTop {

    private String methodName;

    private Long exceptionNum;
}
