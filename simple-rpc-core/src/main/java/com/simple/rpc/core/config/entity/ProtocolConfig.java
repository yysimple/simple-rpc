package com.simple.rpc.core.config.entity;

import com.simple.rpc.common.annotation.SimpleRpcConfig;
import com.simple.rpc.common.constant.enums.CompressType;
import com.simple.rpc.common.constant.enums.SerializeType;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 协议配置
 *
 * @author: WuChengXing
 * @create: 2022-04-30 10:47
 **/
@SimpleRpcConfig(prefix = "simple.rpc.protocol")
public class ProtocolConfig {

    /**
     * 序列化类型 {@link SerializeType}
     */
    private String serializeType;

    /**
     * 压缩类型 {@link CompressType}
     */
    private String compressType;

    public String getSerializeType() {
        return serializeType != null ? serializeType : SerializeType.PROTOSTUFF.getName();
    }

    public String getCompressType() {
        return compressType != null ? compressType : CompressType.DEFAULT.getName();
    }

    public void setSerializeType(String serializeType) {
        this.serializeType = serializeType;
    }

    public void setCompressType(String compressType) {
        this.compressType = compressType;
    }
}
