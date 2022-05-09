package com.simple.rpc.common.constant.enums;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 序列化类型
 *
 * @author: WuChengXing
 * @create: 2022-04-27 15:57
 **/
public enum SerializeType {
    /**
     *
     */
    PROTOSTUFF((byte) 1, "protostuff"),
    SPI((byte) 2, "serializer");

    private final byte value;
    private final String name;

    public static SerializeType fromValue(byte value) {
        for (SerializeType serializeType : SerializeType.values()) {
            if (serializeType.getValue() == value) {
                return serializeType;
            }
        }
        return PROTOSTUFF;
    }

    public static SerializeType fromName(String name) {
        for (SerializeType serializeType : SerializeType.values()) {
            if (serializeType.getName().equals(name)) {
                return serializeType;
            }
        }
        return PROTOSTUFF;
    }

    public byte getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    SerializeType(byte value, String name) {
        this.value = value;
        this.name = name;
    }
}
