package com.simple.rpc.test;

import com.simple.rpc.core.config.ConfigManager;
import com.simple.rpc.core.config.entity.BaseConfig;
import com.simple.rpc.core.config.entity.RegistryConfig;
import com.simple.rpc.core.config.entity.SimpleRpcUrl;
import org.junit.Test;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 配置加载
 *
 * @author: WuChengXing
 * @create: 2022-05-06 16:40
 **/
public class ConfigLoaderTest {

    @Test
    public void getRegistryConfig() {
        RegistryConfig registryConfig = ConfigManager.getInstant().loadConfig(RegistryConfig.class);
        System.out.println(registryConfig);
        System.out.println(SimpleRpcUrl.toSimpleRpcUrl(registryConfig));
    }

    @Test
    public void getBaseConfig() {
        BaseConfig baseConfig = ConfigManager.getInstant().loadConfig(BaseConfig.class);
        System.out.println(baseConfig);
    }
}
