package com.simple.rpc.core.network.message;

import io.netty.channel.Channel;
import lombok.Data;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 响应信息
 *
 * @author: WuChengXing
 * @create: 2022-04-18 18:54
 **/
@Data
public class Response {
    /**
     * 关联通道
     */
    private transient Channel channel;

    /**
     * 此次请求id
     */
    private long requestId;

    /**
     * 异常信息
     */
    private Object exceptionInfo;

    /**
     * 返回的信息
     */
    private Object result;

}
