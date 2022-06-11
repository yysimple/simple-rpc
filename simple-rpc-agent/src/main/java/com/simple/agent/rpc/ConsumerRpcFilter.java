package com.simple.agent.rpc;

import com.simple.agent.trace.Span;
import com.simple.agent.trace.SpanContext;
import com.simple.agent.trace.TrackManager;
import com.simple.rpc.common.interfaces.entity.InvokeFilterInfo;
import com.simple.rpc.common.interfaces.entity.SimpleRpcContext;
import com.simple.rpc.core.filter.InvokeBeforeFilter;

import java.util.Objects;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-06-11 23:25
 **/
public class ConsumerRpcFilter implements InvokeBeforeFilter {
    
    @Override
    public void filter(InvokeFilterInfo invokeFilterInfo) {
        
    }

    @Override
    public SimpleRpcContext invokeBefore(SimpleRpcContext context) {
        Span span = TrackManager.getCurrentSpan();
        context.setTraceId(Objects.isNull(span) ? null : span.getTraceId());
        Span contextSpan = SpanContext.getSpan();
        context.setSpanId(Objects.isNull(contextSpan) ? null : contextSpan.getSpanId());
        context.setLevel(Objects.isNull(contextSpan) ? null : contextSpan.getLevel());
        context.setEnterOrExit(Objects.isNull(contextSpan) ? null : contextSpan.getEnterOrExit());
        return context;
    }
}
