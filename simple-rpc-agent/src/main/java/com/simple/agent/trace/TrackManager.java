package com.simple.agent.trace;

import cn.hutool.core.util.StrUtil;

import java.util.Stack;

/**
 * 项目: class-byte-code
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-06-03 00:47
 **/
public class TrackManager {
    private static final ThreadLocal<Stack<Span>> track = new ThreadLocal<>();

    private static Span createSpan() {
        Stack<Span> stack = track.get();
        if (stack == null) {
            stack = new Stack<>();
            track.set(stack);
        }
        String traceId;
        if (stack.isEmpty()) {
            traceId = TrackContext.getTraceId();
            if (traceId == null) {
                traceId = "nvl";
                TrackContext.setTraceId(traceId);
            }
        } else {
            Span span = stack.peek();
            traceId = span.getTraceId();
            TrackContext.setTraceId(traceId);
        }
        return new Span(traceId);
    }

    public static Span createEntrySpan() {
        Span span = createSpan();
        Stack<Span> stack = track.get();
        stack.push(span);
        return span;
    }


    public static Span getExitSpan() {
        Stack<Span> stack = track.get();
        if (stack == null || stack.isEmpty()) {
            TrackContext.clear();
            return null;
        }
        return stack.pop();
    }

    public static Span getCurrentSpan() {
        if (!StrUtil.isBlank(TrackContext.getTraceId())) {
            return new Span(TrackContext.getTraceId());
        }
        Stack<Span> stack = track.get();
        if (stack == null || stack.isEmpty()) {
            return null;
        }
        return stack.peek();
    }
}
