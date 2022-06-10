package com.simple.agent.trace;

import java.util.Date;

/**
 * 项目: class-byte-code
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-06-03 00:47
 **/
public class Span {

    private String spanId;

    private Integer level;

    private String traceId;

    private Date enterTime;

    /**
     * 1=进入，2=退出
     */
    private Integer enterOrExit;

    public Span() {
    }

    public Span(String traceId) {
        this.traceId = traceId;
        this.enterTime = new Date();
    }

    public Span(String traceId, String spanId, Integer level, Integer enterOrExit) {
        this.traceId = traceId;
        this.spanId = spanId;
        this.level = level;
        this.enterOrExit = enterOrExit;
        this.enterTime = new Date();
    }

    public Span(String traceId, Date enterTime) {
        this.traceId = traceId;
        this.enterTime = enterTime;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public Date getEnterTime() {
        return enterTime;
    }

    public void setEnterTime(Date enterTime) {
        this.enterTime = enterTime;
    }

    public String getSpanId() {
        return spanId;
    }

    public void setSpanId(String spanId) {
        this.spanId = spanId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getEnterOrExit() {
        return enterOrExit;
    }

    public void setEnterOrExit(Integer enterOrExit) {
        this.enterOrExit = enterOrExit;
    }

    @Override
    public String toString() {
        return "Span{" +
                "spanId='" + spanId + '\'' +
                ", level=" + level +
                ", traceId='" + traceId + '\'' +
                ", enterTime=" + enterTime +
                ", enterOrExit=" + enterOrExit +
                '}';
    }
}
