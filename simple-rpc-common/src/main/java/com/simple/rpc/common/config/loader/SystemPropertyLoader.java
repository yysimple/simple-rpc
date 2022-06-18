package com.simple.rpc.common.config.loader;

import com.simple.rpc.common.interfaces.ConfigLoader;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 系统默认配置类
 *
 * @author: WuChengXing
 * @create: 2022-04-30 10:36
 **/
public class SystemPropertyLoader implements ConfigLoader {

    @Override
    public String loadConfigItem(String key) {
        return System.getProperty(key);
    }
}
