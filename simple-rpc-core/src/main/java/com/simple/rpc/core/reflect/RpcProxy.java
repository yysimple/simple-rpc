package com.simple.rpc.core.reflect;

import com.simple.rpc.core.network.message.Request;
import com.simple.rpc.core.util.ClassLoaderUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
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
     * @param request
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> T invoke(Class<T> interfaceClass, Request request) {
        InvocationHandler handler = new RpcInvocationHandler(request);
        ClassLoader classLoader = ClassLoaderUtils.getCurrentClassLoader();
        T result = (T) Proxy.newProxyInstance(classLoader, new Class[]{interfaceClass}, handler);
        return result;
    }

    public static <T> T invoke(Class<T> interfaceClass) {
        ClassLoader classLoader = ClassLoaderUtils.getCurrentClassLoader();
        T result = (T) Proxy.newProxyInstance(classLoader, new Class[]{interfaceClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return proxy;
            }
        });
        return result;
    }
}
