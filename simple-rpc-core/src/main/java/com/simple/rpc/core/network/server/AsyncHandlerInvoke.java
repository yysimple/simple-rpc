package com.simple.rpc.core.network.server;

import lombok.SneakyThrows;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 异步调用
 *
 * @author: WuChengXing
 * @create: 2022-08-29 10:04
 **/
public class AsyncHandlerInvoke implements Runnable {

    Method method;
    Object bean;
    Object[] args;
    Object result;

    public AsyncHandlerInvoke(Method method, Object bean, Object[] args) {
        this.method = method;
        this.bean = bean;
        this.args = args;
    }

    public AsyncHandlerInvoke() {
    }

    @Override
    public void run() {
        try {
            this.result = method.invoke(bean, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
