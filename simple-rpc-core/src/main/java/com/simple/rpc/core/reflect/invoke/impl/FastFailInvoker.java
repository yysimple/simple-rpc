package com.simple.rpc.core.reflect.invoke.impl;

import com.simple.rpc.core.network.message.Request;
import com.simple.rpc.core.network.message.Response;
import com.simple.rpc.core.reflect.invoke.NettyInvoker;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 快速失败
 *
 * @author: WuChengXing
 * @create: 2022-06-04 19:11
 **/
public class FastFailInvoker extends AbstractFaultTolerantInvoker {

    @Override
    protected Response doInvoke(Request request) {
        return NettyInvoker.send(request);
    }
}
