package com.simple.rpc.core.test;

import com.simple.rpc.core.config.ConfigManager;
import com.simple.rpc.core.config.entity.RegistryConfig;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 测试配置加载器
 *
 * @author: WuChengXing
 * @create: 2022-04-30 11:29
 **/
public class ConfigLoaderTest {

    public static void main(String[] args) {
        RegistryConfig registryConfig = ConfigManager.getInstant().loadConfig(RegistryConfig.class);
        System.out.println(registryConfig);
    }
}
