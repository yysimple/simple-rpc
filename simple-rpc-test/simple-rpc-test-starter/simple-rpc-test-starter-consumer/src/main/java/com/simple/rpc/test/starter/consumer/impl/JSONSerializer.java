package com.simple.rpc.test.starter.consumer.impl;

import cn.hutool.json.JSONUtil;
import com.simple.rpc.core.serializer.Serializer;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-05-08 12:30
 **/
public class JSONSerializer implements Serializer {
    @Override
    public byte[] serialize(Object object) {
        return JSONUtil.toJsonStr(object).getBytes();
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        return JSONUtil.toBean(new String(bytes), clazz);
    }

}
