package com.simple.rpc.test.starter.provider.filter;

import com.simple.rpc.common.interfaces.entity.InvokeFilterInfo;
import com.simple.rpc.common.interfaces.entity.SimpleRpcContext;
import com.simple.rpc.common.util.SimpleRpcLog;
import com.simple.rpc.core.filter.RemoteInvokeBeforeFilter;
import com.simple.rpc.core.network.message.Request;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-06-05 14:06
 **/
public class ProviderRemoteInvokerFilter implements RemoteInvokeBeforeFilter {
    @Override
    public void filter(InvokeFilterInfo invokeFilterInfo) {

    }

    @Override
    public SimpleRpcContext invokeBefore(SimpleRpcContext context) {
        SimpleRpcLog.info("=== 我是远程调用之前的过滤器 ===");
        return context;
    }
}
