package com.simple.rpc.common.interfaces.entity;

import lombok.Data;

import java.util.Date;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 上下文信息
 *
 * @author: WuChengXing
 * @create: 2022-06-05 14:31
 **/
@Data
public class SimpleRpcContext {

    private String traceId;

    private Date entryTime;

    private String spanId;

    private Integer level;

    /**
     * 1=进入，2=退出
     */
    private Integer enterOrExit;
}
