package com.simple.rpc.test;

import com.simple.rpc.common.interfaces.ConfigLoader;
import com.simple.rpc.common.interfaces.RegisterCenter;
import com.simple.rpc.common.interfaces.Serializer;
import com.simple.rpc.common.spi.ExtensionLoader;
import org.junit.Test;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-05-07 17:08
 **/
public class ExtensionLoaderTest {

    @Test
    public void testSerialize() {
        System.out.println(ExtensionLoader.getLoader(Serializer.class).getExtension("json"));
        System.out.println(ExtensionLoader.getLoader(RegisterCenter.class).getExtension("mysql"));
        System.out.println(ExtensionLoader.getLoader(ConfigLoader.class).getExtension("spi-property"));
    }
}
