package com.simple.rpc.core.network.cache;

import cn.hutool.core.util.StrUtil;
import com.simple.rpc.core.exception.SimpleRpcBaseException;
import com.simple.rpc.core.util.SimpleRpcLog;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: rpc服务缓存
 *
 * @author: WuChengXing
 * @create: 2022-04-29 17:43
 **/
public class SimpleRpcServiceCache {

    private static final Map<String, Object> SERVICE_MAP = new ConcurrentHashMap<>();

    public static void addService(String version, Object service) {
        Class<?>[] interfaces = service.getClass().getInterfaces();
        if (interfaces.length == 0) {
            throw new SimpleRpcBaseException("add service error. service not implements interface. service=" + service.getClass().getName());
        }
        String rpcServiceName;
        if (StrUtil.isBlank(version)) {
            rpcServiceName = interfaces[0].getCanonicalName();
        } else {
            rpcServiceName = interfaces[0].getCanonicalName() + "_" + version;
        }
        SERVICE_MAP.putIfAbsent(rpcServiceName, service);
        SimpleRpcLog.info(StrUtil.format("add service. rpcServiceName={}, class={}", rpcServiceName, service.getClass()));
    }

    public static Object getService(String rpcServiceName) {
        Object service = SERVICE_MAP.get(rpcServiceName);
        if (service == null) {
            throw new SimpleRpcBaseException("rpcService not found." + rpcServiceName);
        }
        return service;
    }
}
