package com.simple.rpc.core.network.message;


import com.simple.rpc.common.constant.enums.MessageType;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: Rpc请求参数
 *
 * @author: WuChengXing
 * @create: 2022-04-26 22:02
 **/
public class RpcMessage {

    /**
     * 消息类型 {@link MessageType}
     */
    private byte messageType;

    /**
     * 压缩类型
     */
    private byte compressTye;

    /**
     * 序列化类型
     */
    private byte serializeType;

    /**
     * 请求id
     */
    private long requestId;

    /**
     * 具体数据
     */
    private Object data;

    public static RpcMessage copy(RpcMessage var1) {
        RpcMessage var2 = new RpcMessage();
        var2.setMessageType(var1.getMessageType());
        var2.setCompressTye(var1.getCompressTye());
        var2.setSerializeType(var1.getSerializeType());
        var2.setRequestId(var1.getRequestId());
        return var2;
    }

    public RpcMessage() {
    }

    public RpcMessage(byte messageType, byte compressTye, byte serializeType, long requestId, Object data) {
        this.messageType = messageType;
        this.compressTye = compressTye;
        this.serializeType = serializeType;
        this.requestId = requestId;
        this.data = data;
    }

    public byte getMessageType() {
        return messageType;
    }

    public void setMessageType(byte messageType) {
        this.messageType = messageType;
    }

    public byte getCompressTye() {
        return compressTye;
    }

    public void setCompressTye(byte compressTye) {
        this.compressTye = compressTye;
    }

    public byte getSerializeType() {
        return serializeType;
    }

    public void setSerializeType(byte serializeType) {
        this.serializeType = serializeType;
    }

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
