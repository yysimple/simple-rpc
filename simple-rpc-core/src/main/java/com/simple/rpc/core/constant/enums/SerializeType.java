package com.simple.rpc.core.constant.enums;

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
    PROTOSTUFF((byte) 1, "protostuff");

    private final byte value;
    private final String name;

    public static SerializeType fromValue(byte value) {
        for (SerializeType serializeType : SerializeType.values()) {
            if (serializeType.getValue() == value) {
                return serializeType;
            }
        }
        return null;
    }

    public static SerializeType fromName(String name) {
        for (SerializeType serializeType : SerializeType.values()) {
            if (serializeType.getName().equals(name)) {
                return serializeType;
            }
        }
        return null;
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
