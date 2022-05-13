package com.simple.rpc.test.core.common.test;

import com.simple.rpc.core.config.ConfigManager;
import com.simple.rpc.common.config.RegistryConfig;
import com.simple.rpc.common.config.SimpleRpcUrl;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-04-30 11:54
 **/
public class ConfigLoaderTest {

    public static void main(String[] args) {
        RegistryConfig registryConfig = ConfigManager.getInstant().loadConfig(RegistryConfig.class);
        System.out.println(registryConfig);
        System.out.println(SimpleRpcUrl.toSimpleRpcUrl(registryConfig));
    }
}
