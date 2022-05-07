package com.simple.rpc.test;

import com.simple.rpc.core.spi.ExtensionLoader;
import com.simple.rpc.test.core.common.test.spi.Serializer;
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
    }
}
