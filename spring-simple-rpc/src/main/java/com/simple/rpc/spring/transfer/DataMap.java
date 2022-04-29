package com.simple.rpc.spring.transfer;

import com.simple.rpc.core.register.config.RegisterProperties;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 数据缓存
 *
 * @author: WuChengXing
 * @create: 2022-04-23 14:21
 **/
public class DataMap {

    public static final String DATA_TRANSFER = "dataTransfer";
    public static final String BASE_DATA_TRANSFER = "baseDataTransfer";

    public static Map<String, RegisterProperties> dataTransfer = new ConcurrentHashMap<>(4);
    public static Map<String, BaseData> baseDataTransfer = new ConcurrentHashMap<>(4);
}
