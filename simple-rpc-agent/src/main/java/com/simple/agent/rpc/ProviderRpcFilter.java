package com.simple.agent.rpc;

import com.simple.agent.trace.Span;
import com.simple.agent.trace.SpanContext;
import com.simple.agent.trace.TrackContext;
import com.simple.rpc.common.interfaces.entity.InvokeFilterInfo;
import com.simple.rpc.common.interfaces.entity.SimpleRpcContext;
import com.simple.rpc.core.filter.RemoteInvokeBeforeFilter;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-06-11 23:25
 **/
public class ProviderRpcFilter implements RemoteInvokeBeforeFilter {

    @Override
    public void filter(InvokeFilterInfo invokeFilterInfo) {

    }

    @Override
    public SimpleRpcContext invokeBefore(SimpleRpcContext request) {
        TrackContext.setTraceId(request.getTraceId());
        Span rpcSpan = new Span(request.getTraceId(), request.getSpanId(), request.getLevel(), request.getEnterOrExit());
        SpanContext.setSpan(rpcSpan);
        return request;
    }
}
