package com.simple.rpc.common.cache;

import com.simple.rpc.common.cache.entity.ApplicationEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-06-17 23:57
 **/
public class ApplicationCache {

    public static String APPLICATION_NAME;

    /**
     * 应用名
     */
    public static Map<String, ApplicationEntity> APPLICATION_MAP = new HashMap<>(4);

    public static Boolean add(String hostPort, ApplicationEntity entity, boolean isOverride) {
        ApplicationEntity applicationEntity = APPLICATION_MAP.get(hostPort);
        if (Objects.isNull(applicationEntity)) {
            APPLICATION_MAP.put(hostPort, entity);
            return true;
        }
        if (isOverride) {
            APPLICATION_MAP.put(hostPort, entity);
        } else {
            APPLICATION_MAP.putIfAbsent(hostPort, entity);
        }
        return true;
    }

    public static ApplicationEntity get(String hostPort) {
        ApplicationEntity applicationEntity = APPLICATION_MAP.get(hostPort);
        if (Objects.isNull(applicationEntity)) {
            return new ApplicationEntity();
        } else {
            return applicationEntity;
        }
    }
}
