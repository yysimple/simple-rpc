package com.simple.rpc.core.reflect.invoke;

import cn.hutool.core.util.StrUtil;
import com.simple.rpc.common.exception.network.NettyInvokeException;
import com.simple.rpc.common.exception.network.NettyResponseException;
import com.simple.rpc.common.util.SimpleRpcLog;
import com.simple.rpc.core.network.message.Request;
import com.simple.rpc.core.network.message.Response;
import com.simple.rpc.core.network.send.SyncWrite;

import java.util.Objects;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: netty的invoker
 *
 * @author: WuChengXing
 * @create: 2022-06-04 17:02
 **/
public class NettyInvoker {

    public static Response send(Request request) {
        // 发送请求
        Response response = null;
        try {
            response = new SyncWrite().writeAndSync(request.getChannel(), request,
                    Objects.isNull(request.getTimeout()) ? 30L : request.getTimeout());
        } catch (Exception e) {
            throw new NettyInvokeException(e.getMessage());
        }

        //异步调用
        return response;
    }
}
