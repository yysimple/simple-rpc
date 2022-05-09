package com.simple.rpc.common.constant.enums;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 消息类型
 *
 * @author: WuChengXing
 * @create: 2022-04-27 15:47
 **/
public enum MessageType {
    /**
     * 普通请求
     */
    REQUEST((byte) 1),

    /**
     * 普通响应
     */
    RESPONSE((byte) 2),

    /**
     * 心跳
     */
    HEARTBEAT((byte) 3),
    ;
    private final byte value;

    public byte getValue() {
        return value;
    }

    MessageType(byte value) {
        this.value = value;
    }
}
