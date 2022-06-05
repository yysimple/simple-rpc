package com.simple.rpc.test.starter.consumer.filter;

import com.simple.rpc.common.interfaces.entity.InvokeFilterInfo;
import com.simple.rpc.common.interfaces.entity.SimpleRpcContext;
import com.simple.rpc.common.util.SimpleRpcLog;
import com.simple.rpc.core.filter.InvokeBeforeFilter;
import com.simple.rpc.core.network.message.Request;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-06-05 12:28
 **/
public class ConsumerInvokeFilter implements InvokeBeforeFilter {

    @Override
    public void filter(InvokeFilterInfo invokeFilterInfo) {

    }

    @Override
    public SimpleRpcContext invokeBefore(SimpleRpcContext context) {
        SimpleRpcLog.info("=== 我是调用之前的拦截 ===");
        return context;
    }
}
