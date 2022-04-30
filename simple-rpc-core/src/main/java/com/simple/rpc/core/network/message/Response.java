package com.simple.rpc.core.network.message;

import io.netty.channel.Channel;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 响应信息
 *
 * @author: WuChengXing
 * @create: 2022-04-18 18:54
 **/
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
     * 返回的信息
     */
    private Object result;

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
