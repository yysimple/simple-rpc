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

    public static void addService(String alias, Object service) {
        SERVICE_MAP.putIfAbsent(alias, service);
        SimpleRpcLog.info(StrUtil.format("add service. alias={}, class={}", alias, service.getClass()));
    }

    public static Object getService(String alias) {
        Object service = SERVICE_MAP.get(alias);
        if (service == null) {
            throw new SimpleRpcBaseException("rpcService not found." + alias);
        }
        return service;
    }
}
