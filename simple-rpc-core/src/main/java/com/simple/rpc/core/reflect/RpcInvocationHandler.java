package com.simple.rpc.core.reflect;

import com.simple.rpc.core.constant.JavaKeywordConstant;
import com.simple.rpc.core.network.message.Request;
import com.simple.rpc.core.network.message.Response;
import com.simple.rpc.core.network.send.SyncWrite;
import com.simple.rpc.core.util.SimpleRpcLog;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: rpc代理类
 *
 * @author: WuChengXing
 * @create: 2022-04-19 16:45
 **/
public class RpcInvocationHandler implements InvocationHandler {

    private final Request request;

    public RpcInvocationHandler(Request request) {
        this.request = request;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String methodName = method.getName();
        Class[] paramTypes = method.getParameterTypes();
        // 排除Object的方法调用
        if (JavaKeywordConstant.TO_STRING.equals(methodName) && paramTypes.length == 0) {
            return request.toString();
        } else if (JavaKeywordConstant.HASHCODE.equals(methodName) && paramTypes.length == 0) {
            return request.hashCode();
        } else if (JavaKeywordConstant.EQUALS.equals(methodName) && paramTypes.length == 1) {
            return request.equals(args[0]);
        }
        //设置参数
        request.setMethodName(methodName);
        request.setParamTypes(paramTypes);
        request.setArgs(args);
        request.setBeanName(request.getBeanName());
        // 发送请求
        Response response = null;
        try {
            response = new SyncWrite().writeAndSync(request.getChannel(), request,
                    Objects.isNull(request.getTimeout()) ? 30L : request.getTimeout());
        } catch (Exception e) {
            e.printStackTrace();
            SimpleRpcLog.error(e.getMessage());
        }

        //异步调用
        return response.getResult();
    }
}
