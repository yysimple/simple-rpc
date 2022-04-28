package com.simple.rpc.core.constant.enums;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 压缩类型
 *
 * @author: WuChengXing
 * @create: 2022-04-27 15:52
 **/
public enum CompressType {

    /**
     *
     */
    GZIP((byte) 1, "gzip");

    private final byte value;
    private final String name;

    /**
     * 通过值获取压缩类型枚举
     *
     * @param value 值
     * @return 如果获取不到，返回 GZIP
     */
    public static CompressType fromValue(byte value) {
        for (CompressType codecType : CompressType.values()) {
            if (codecType.getValue() == value) {
                return codecType;
            }
        }
        return GZIP;
    }

    /**
     * 通过名字获取压缩类型枚举
     *
     * @param name 名字
     * @return 如果获取不到，返回 GZIP
     */
    public static CompressType fromName(String name) {
        for (CompressType codecType : CompressType.values()) {
            if (codecType.getName().equals(name)) {
                return codecType;
            }
        }
        return GZIP;
    }

    public byte getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    CompressType(byte value, String name) {
        this.value = value;
        this.name = name;
    }
}
