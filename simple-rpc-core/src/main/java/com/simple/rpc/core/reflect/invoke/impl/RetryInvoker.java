package com.simple.rpc.core.reflect.invoke.impl;

import com.simple.rpc.core.network.message.Request;
import com.simple.rpc.core.network.message.Response;
import com.simple.rpc.core.reflect.invoke.NettyInvoker;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 重试机制
 *
 * @author: WuChengXing
 * @create: 2022-06-04 20:12
 **/
public class RetryInvoker extends AbstractFaultTolerantInvoker {

    @Override
    protected Response doInvoke(Request request) {
        Response response = null;
        for (int i = 0; i < request.getRetryNum(); i++) {
            response = NettyInvoker.send(request);
        }
        return response;
    }
}
