package com.simple.rpc.test.core.common.test.spi;

import com.simple.rpc.core.annotation.SimpleRpcSPI;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-05-07 17:29
 **/
@SimpleRpcSPI(value = "json")
public interface Serializer {
    /**
     * 序列化
     *
     * @param object 要序列化的对象
     * @return 字节数组
     */
    byte[] serialize(Object object);

    /**
     * 反序列化
     *
     * @param bytes 字节数组
     * @param clazz 要反序列化的类
     * @param <T>   类型
     * @return 反序列化的对象
     */
    <T> T deserialize(byte[] bytes, Class<T> clazz);
}
