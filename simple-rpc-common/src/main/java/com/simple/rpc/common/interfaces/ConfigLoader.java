package com.simple.rpc.common.interfaces;

import com.simple.rpc.common.annotation.SimpleRpcSPI;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 配置加载器
 *
 * @author: WuChengXing
 * @create: 2022-04-30 10:34
 **/
@SimpleRpcSPI(value = "simple-rpc-property")
public interface ConfigLoader {

    /**
     * 加载配置项
     *
     * @param key 配置的 key
     * @return 配置项的值，如果不存在，返回 null
     */
    String loadConfigItem(String key);
}
