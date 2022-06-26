package com.simple.agent.data;

import com.simple.agent.constant.AgentConstant;
import com.simple.rpc.common.interfaces.DataCollection;
import com.simple.rpc.common.interfaces.entity.CollectData;
import com.simple.rpc.common.util.NetUtil;

import java.net.UnknownHostException;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-06-16 23:22
 **/
public class DataBuilder {

    static ExecutorService service = Executors.newFixedThreadPool(10);

    public static void buildEntry(String appName, String clazzName, String methodName,
                                  String traceId, String spanId, String parentSpanId,
                                  Date entryTime, Integer level, String requestInfo, Integer invoker) {
        CollectData data = new CollectData();
        data.setAppName(appName);
        data.setClazzName(clazzName);
        data.setMethodName(methodName);
        data.setTraceId(traceId);
        data.setSpanId(spanId);
        data.setParentSpanId(parentSpanId);
        data.setEntryTime(entryTime);
        data.setLevel(level);
        data.setRequestInfo(requestInfo);
        data.setInvoker(invoker);
        data.setExceptionInfo("");
        data.setInvokeStatus(AgentConstant.ENTRY);
        try {
            data.setHost(NetUtil.getHost());
        } catch (UnknownHostException ignored) {
        }
        service.submit(() -> {
            for (DataCollection dataCollection : DataCollectionCache.DATA_COLLECTION_MAP) {
                dataCollection.collect(data);
            }
        });
    }

    public static void buildExist(String appName, String clazzName, String methodName,
                                  String traceId, String spanId, String parentSpanId,
                                  Date exitTime, Integer level, String resultInfo,
                                  String exceptionInfo) {
        CollectData data = new CollectData();
        data.setAppName(appName);
        data.setClazzName(clazzName);
        data.setMethodName(methodName);
        data.setTraceId(traceId);
        data.setSpanId(spanId);
        data.setParentSpanId(parentSpanId);
        data.setExitTime(exitTime);
        data.setLevel(level);
        data.setResultInfo(resultInfo);
        data.setExceptionInfo(exceptionInfo);
        data.setRequestInfo("");
        data.setInvoker(0);
        data.setInvokeStatus(AgentConstant.EXIT);
        try {
            data.setHost(NetUtil.getHost());
        } catch (UnknownHostException ignored) {
        }
        service.submit(() -> {
            for (DataCollection dataCollection : DataCollectionCache.DATA_COLLECTION_MAP) {
                dataCollection.collect(data);
            }
        });
    }
}
