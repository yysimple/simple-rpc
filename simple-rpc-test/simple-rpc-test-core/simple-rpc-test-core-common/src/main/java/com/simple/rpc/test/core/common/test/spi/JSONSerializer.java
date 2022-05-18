package com.simple.rpc.test.core.common.test.spi;

import cn.hutool.json.JSONUtil;
import com.simple.rpc.common.interfaces.Serializer;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: spi机制测试
 *
 * @author: WuChengXing
 * @create: 2022-05-07 17:03
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
