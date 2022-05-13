package com.simple.rpc.core.network.cache;

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

    public static void addService(String alias, Object service) {
        SERVICE_MAP.putIfAbsent(alias, service);
    }

    public static Object getService(String alias) {
        Object service = SERVICE_MAP.get(alias);
        if (service == null) {
            throw new SimpleRpcBaseException("rpcService not found." + alias);
        }
        return service;
    }

    public static List<String> allKey() {
        List<String> allKey = new ArrayList<>(10);
        SERVICE_MAP.forEach((k, v) -> {
            Class<?>[] interfaces = v.getClass().getInterfaces();
            for (Class<?> anInterface : interfaces) {
                allKey.add(anInterface.getCanonicalName() + SymbolConstant.UNDERLINE + k);
            }
        });
        return allKey;
    }
}
