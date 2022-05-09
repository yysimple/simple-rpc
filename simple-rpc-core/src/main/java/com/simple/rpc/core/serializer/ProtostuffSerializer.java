package com.simple.rpc.core.serializer;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.simple.rpc.common.interfaces.Serializer;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 序列化实现
 *
 * @author: WuChengXing
 * @create: 2022-05-02 12:16
 **/
public class ProtostuffSerializer implements Serializer {

    private static final LinkedBuffer BUFFER = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);

    @Override
    public byte[] serialize(Object object) {
        Schema schema = RuntimeSchema.getSchema(object.getClass());
        try {
            return ProtostuffIOUtil.toByteArray(object, schema, BUFFER);
        } finally {
            BUFFER.clear();
        }
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        Schema<T> schema = RuntimeSchema.getSchema(clazz);
        T obj = schema.newMessage();
        ProtostuffIOUtil.mergeFrom(bytes, obj, schema);
        return obj;
    }
}
