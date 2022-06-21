package com.simple.rpc.common.cache;

import com.simple.rpc.common.constant.SymbolConstant;
import com.simple.rpc.common.exception.SimpleRpcBaseException;

import java.util.ArrayList;
import java.util.List;
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

    public static void addService(String registerKey, Object service) {
        SERVICE_MAP.putIfAbsent(registerKey, service);
    }

    public static Object getService(String registerKey) {
        Object service = SERVICE_MAP.get(registerKey);
        if (service == null) {
            throw new SimpleRpcBaseException("rpcService not found." + registerKey);
        }
        return service;
    }

    public static List<String> allKey() {
        return new ArrayList<>(SERVICE_MAP.keySet());
    }
}
