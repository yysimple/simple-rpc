package com.simple.rpc.core.spring;

import com.simple.rpc.core.register.config.RegisterProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 数据缓存
 *
 * @author: WuChengXing
 * @create: 2022-04-23 14:21
 **/
public class DataMap {

    public static Map<String, RegisterProperties> dataTransfer = new HashMap<>(4);
}
