package com.simple.rpc.test;

import com.simple.rpc.common.config.BaseConfig;
import com.simple.rpc.common.config.RegistryConfig;
import com.simple.rpc.common.config.SimpleRpcUrl;
import com.simple.rpc.common.interfaces.ConfigLoader;
import com.simple.rpc.common.config.ConfigManager;
import com.simple.rpc.common.spi.ExtensionLoader;
import org.junit.Test;

import java.util.Properties;

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

    @Test
    public void getSystemConfig() {
        Properties properties = new Properties();
        properties.setProperty("weights", "10");
        System.setProperties(properties);
        ExtensionLoader.getLoader(ConfigLoader.class).getExtension("system-property");
        BaseConfig baseConfig = ConfigManager.getInstant().loadConfig(BaseConfig.class);
        System.out.println(baseConfig);
    }

}
