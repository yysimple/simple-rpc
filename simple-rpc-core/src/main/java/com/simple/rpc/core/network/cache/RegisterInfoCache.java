package com.simple.rpc.core.network.cache;

import com.simple.rpc.common.config.RegistryConfig;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 注册中心数据缓存
 *
 * @author: WuChengXing
 * @create: 2022-05-04 17:42
 **/
public class RegisterInfoCache {

    private static Map<String, Object> SERVICE_MAP = new ConcurrentHashMap<>();
    private static final String CACHE_KEY = "register_info";

    public static <T extends RegistryConfig> T getRegisterInfo() {
        return (T) SERVICE_MAP.get(CACHE_KEY);
    }

    public static <T extends RegistryConfig> void save(T config) {
        SERVICE_MAP.put(CACHE_KEY, config);
    }
}
