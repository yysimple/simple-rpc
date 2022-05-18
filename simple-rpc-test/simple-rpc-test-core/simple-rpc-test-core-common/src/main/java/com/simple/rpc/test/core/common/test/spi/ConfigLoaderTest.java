package com.simple.rpc.test.core.common.test.spi;

import com.simple.rpc.common.interfaces.ConfigLoader;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-05-14 12:44
 **/
public class ConfigLoaderTest implements ConfigLoader {
    @Override
    public String loadConfigItem(String key) {
        System.out.println("--- 走的是SPI的方式 ---");
        return null;
    }
}
