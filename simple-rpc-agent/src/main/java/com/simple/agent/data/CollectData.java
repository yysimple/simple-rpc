package com.simple.agent.data;

import java.util.Date;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 收集的数据实体
 *
 * @author: WuChengXing
 * @create: 2022-06-14 20:48
 **/
public class CollectData {

    private String traceId;

    private String spanId;

    private String parentSpanId;

    private Integer level;

    private Date entryTime;

    private Date exitTime;

    private String appName;

    private String clazzName;

    private String methodName;

    private String requestInfo;

    private String resultInfo;

    private String exceptionInfo;

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getSpanId() {
        return spanId;
    }

    public void setSpanId(String spanId) {
        this.spanId = spanId;
    }

    public String getParentSpanId() {
        return parentSpanId;
    }

    public void setParentSpanId(String parentSpanId) {
        this.parentSpanId = parentSpanId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Date getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(Date entryTime) {
        this.entryTime = entryTime;
    }

    public Date getExitTime() {
        return exitTime;
    }

    public void setExitTime(Date exitTime) {
        this.exitTime = exitTime;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getClazzName() {
        return clazzName;
    }

    public void setClazzName(String clazzName) {
        this.clazzName = clazzName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getRequestInfo() {
        return requestInfo;
    }

    public void setRequestInfo(String requestInfo) {
        this.requestInfo = requestInfo;
    }

    public String getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(String resultInfo) {
        this.resultInfo = resultInfo;
    }

    public String getExceptionInfo() {
        return exceptionInfo;
    }

    public void setExceptionInfo(String exceptionInfo) {
        this.exceptionInfo = exceptionInfo;
    }

    @Override
    public String toString() {
        return "CollectData{" +
                "traceId='" + traceId + '\'' +
                ", spanId='" + spanId + '\'' +
                ", parentSpanId='" + parentSpanId + '\'' +
                ", level=" + level +
                ", entryTime=" + entryTime +
                ", exitTime=" + exitTime +
                ", appName='" + appName + '\'' +
                ", clazzName='" + clazzName + '\'' +
                ", methodName='" + methodName + '\'' +
                ", requestInfo='" + requestInfo + '\'' +
                ", resultInfo='" + resultInfo + '\'' +
                ", exceptionInfo='" + exceptionInfo + '\'' +
                '}';
    }
}
