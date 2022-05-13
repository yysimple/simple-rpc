package com.simple.rpc.core.reflect;

import com.simple.rpc.common.util.ClassLoaderUtils;
import com.simple.rpc.common.config.CommonConfig;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 代理类
 *
 * @author: WuChengXing
 * @create: 2022-04-19 17:11
 **/
public class RpcProxy {

    /**
     * 通过带jdk动态代理接口
     *
     * @param interfaceClass
     * @param config
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> T invoke(Class<T> interfaceClass, CommonConfig config) {
        InvocationHandler handler = new RpcInvocationHandler(config);
        ClassLoader classLoader = ClassLoaderUtils.getCurrentClassLoader();
        T result = (T) Proxy.newProxyInstance(classLoader, new Class[]{interfaceClass}, handler);
        return result;
    }
}
