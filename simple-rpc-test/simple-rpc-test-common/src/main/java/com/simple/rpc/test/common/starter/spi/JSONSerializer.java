package com.simple.rpc.test.common.starter.spi;

import cn.hutool.json.JSONUtil;
import com.simple.rpc.common.interfaces.Serializer;
import com.simple.rpc.common.util.SimpleRpcLog;
import org.apache.commons.logging.impl.SimpleLog;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-05-09 09:49
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
