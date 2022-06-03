package com.simple.rpc.test;

import com.simple.rpc.common.interfaces.Serializer;
import com.simple.rpc.core.network.message.Request;
import com.simple.rpc.core.network.message.RpcMessage;
import com.simple.rpc.core.serializer.ProtostuffSerializer;
import org.junit.Test;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 序列化测试
 *
 * @author: WuChengXing
 * @create: 2022-05-26 23:39
 **/
public class SerializeTest {

    @Test
    public void test01() {
        Serializer serializer = new ProtostuffSerializer();
        RpcMessage rpcMessage = new RpcMessage();
        Request request = new Request();
        Class[] classes = {String.class, Integer.class};
        //request.setParamTypes(classes);
        rpcMessage.setData(request);
        byte[] serialize = serializer.serialize(rpcMessage);
        RpcMessage deserialize = serializer.deserialize(serialize, RpcMessage.class);
        Request request1 = (Request)deserialize.getData();
        System.out.println(request1);
    }

}
