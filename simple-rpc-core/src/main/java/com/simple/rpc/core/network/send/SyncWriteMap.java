package com.simple.rpc.core.network.send;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 连接缓存
 *
 * @author: WuChengXing
 * @create: 2022-04-19 15:03
 **/
public class SyncWriteMap {
    /**
     * 存储连接的缓存
     */
    public static Map<Long, WriteFuture> syncKey = new ConcurrentHashMap<>();
}
