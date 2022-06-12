package com.simple.agent.plugins.impl.trace;

import com.alibaba.fastjson.JSON;
import com.simple.agent.trace.Span;
import com.simple.agent.trace.SpanContext;
import com.simple.agent.trace.TrackContext;
import com.simple.agent.trace.TrackManager;
import com.simple.agent.util.AgentLog;
import net.bytebuddy.asm.Advice;

import java.util.Objects;
import java.util.UUID;

/**
 * 项目: class-byte-code
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-06-03 00:48
 **/
public class TraceAdvice {

    @Advice.OnMethodEnter()
    public static void enter(@Advice.Origin("#t") String className,
                             @Advice.Origin("#m") String methodName,
                             @Advice.AllArguments Object[] args) {
        AgentLog.info("进入方法，类方法信息：{}, 参数：{}", className + "#" + methodName, JSON.toJSONString(args));
        Span currentSpan = TrackManager.getCurrentSpan();
        String traceId = "";
        if (null == currentSpan) {
            traceId = UUID.randomUUID().toString();
            TrackContext.setTraceId(traceId);
        }
        Span spanContext = SpanContext.getSpan();
        if (spanContext == null) {
            spanContext = new Span(traceId, "0", 0, 1);
        } else {
            spanContext = SpanContext.calEntrySpan(spanContext);
        }
        SpanContext.setSpan(spanContext);
        TrackManager.createEntrySpan();
        AgentLog.info("进入方法，当前Span信息：{}", JSON.toJSONString(spanContext));
    }

    @Advice.OnMethodExit(onThrowable = Throwable.class)
    public static void exit(@Advice.Origin("#t") String className,
                            @Advice.Origin("#m") String methodName,
                            @Advice.Return String returnInfo,
                            @Advice.Thrown Throwable thrown) {
        AgentLog.info("退出方法，类方法信息：{}， 返回值信息：{}", className + "#" + methodName, returnInfo);
        if (!Objects.isNull(thrown)) {
            AgentLog.error("异常类型：{}, 异常信息：{}", thrown.getClass().getName(), thrown.getMessage());
        }
        Span spanContext = SpanContext.getSpan();
        spanContext = SpanContext.calExitSpan(spanContext);
        SpanContext.setSpan(spanContext);
        Span exitSpan = TrackManager.getExitSpan();
        if (null == exitSpan) {
            return;
        }
        AgentLog.info("推出方法，当前Span信息：{}", JSON.toJSONString(spanContext));
    }
}
