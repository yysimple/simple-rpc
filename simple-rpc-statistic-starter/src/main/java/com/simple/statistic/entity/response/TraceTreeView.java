package com.simple.statistic.entity.response;

import lombok.Data;

import java.util.List;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-06-26 23:53
 **/
@Data
public class TraceTreeView {

    private Long id;

    private String traceId;

    private String spanId;

    private String clazzName;

    private String methodName;

    private String showTitle;

    private String idAndTraceId;

    private String parentSpanId;

    private List<TraceTreeView> children;
}
