package com.simple.rpc.core.reflect;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.simple.rpc.common.cache.ApplicationCache;
import com.simple.rpc.common.config.*;
import com.simple.rpc.common.constant.JavaKeywordConstant;
import com.simple.rpc.common.exception.SimpleRpcBaseException;
import com.simple.rpc.common.exception.network.NettyInitException;
import com.simple.rpc.common.exception.network.NettyInvokeException;
import com.simple.rpc.common.interfaces.RegisterCenter;
import com.simple.rpc.common.interfaces.entity.RegisterInfo;
import com.simple.rpc.common.util.SimpleRpcLog;
import com.simple.rpc.core.network.cache.CacheUtil;
import com.simple.rpc.core.network.cache.ConnectCache;
import com.simple.rpc.core.network.client.RpcClientSocket;
import com.simple.rpc.core.network.message.Request;
import com.simple.rpc.core.network.message.Response;
import com.simple.rpc.core.reflect.invoke.MultiInvoker;
import com.simple.rpc.core.register.RegisterCenterFactory;
import io.netty.channel.ChannelFuture;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: rpc代理类
 *
 * @author: WuChengXing
 * @create: 2022-04-19 16:45
 **/
public class RpcInvocationHandler implements InvocationHandler {

    private final CommonConfig commonConfig;

    ExecutorService executorService = Executors.newFixedThreadPool(10);
    ExecutorService invokeThreadPool = Executors.newFixedThreadPool(10);

    public RpcInvocationHandler(CommonConfig commonConfig) {
        this.commonConfig = commonConfig;
    }

    private void connect(Request request) {
        // 从channel缓存中获取channel
        ChannelFuture channelFuture = ConnectCache.getChannelFuture(request);
        if (channelFuture != null && channelFuture.channel().isOpen()) {
            return;
        }
        synchronized (this) {
            if (channelFuture != null && channelFuture.channel().isOpen()) {
                return;
            }
            CacheUtil.deleteConnect(channelFuture);
            channelFuture = ConnectCache.getChannelFuture(request);
            //获取通信channel
            RpcClientSocket clientSocket = new RpcClientSocket(request);
            executorService.submit(clientSocket);
            int tryNum = Objects.isNull(request.getRetryNum()) || request.getRetryNum() <= 0 ? 100 : request.getRetryNum();
            for (int i = 0; i < tryNum; i++) {
                if (null != channelFuture) {
                    break;
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                channelFuture = clientSocket.getFuture();
            }
        }
        if (null == channelFuture) {
            throw new NettyInitException("客户端未连接上服务端，考虑增加重试次数");
        }
        request.setChannelFuture(channelFuture);
        ConnectCache.saveChannelFuture(request);
    }

    private Request buildRequest() {
        ConsumerConfig consumerConfig = commonConfig.getConsumerConfig();
        RegistryConfig registryConfig = commonConfig.getRegistryConfig();
        BaseConfig baseConfig = commonConfig.getBaseConfig();
        // 构建请求参数
        Request request = new Request();
        request.setApplicationName(ApplicationCache.APPLICATION_NAME);
        request.setInterfaceName(consumerConfig.getInterfaceName());
        request.setAlias(consumerConfig.getAlias());
        request.setLoadBalanceRule(baseConfig.getLoadBalanceRule());
        SimpleRpcUrl simpleRpcUrl = SimpleRpcUrl.toSimpleRpcUrl(registryConfig);
        RegisterCenter registerCenter = RegisterCenterFactory.create(simpleRpcUrl.getType());
        if (Objects.isNull(registerCenter)) {
            throw new SimpleRpcBaseException("注册中心未初始化");
        }
        //从redis获取链接
        String infoStr = registerCenter.get(Request.request2Register(request));
        if (Objects.isNull(infoStr)) {
            return null;
        }
        RegisterInfo registerInfo = JSON.parseObject(infoStr, RegisterInfo.class);
        Request returnRequest = Request.register2Request(registerInfo);
        returnRequest.setBeatIntervalTime(baseConfig.getBeatIntervalTime());
        returnRequest.setRetryNum(baseConfig.getRetryNum());
        return returnRequest;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Request buildRequest = buildRequest();
        if (Objects.isNull(buildRequest)) {
            return null;
        }
        // 连接客户端
        connect(buildRequest);
        String methodName = method.getName();
        Class[] paramTypes = method.getParameterTypes();
        List<String> paramTypeStrs = new ArrayList<>();
        if (paramTypes.length > 0) {
            for (int i = 0; i < paramTypes.length; i++) {
                paramTypeStrs.add(paramTypes[i].getCanonicalName());
            }
        }
        // 排除Object的方法调用
        if (JavaKeywordConstant.TO_STRING.equals(methodName) && paramTypes.length == 0) {
            return this.toString();
        } else if (JavaKeywordConstant.HASHCODE.equals(methodName) && paramTypes.length == 0) {
            return this.hashCode();
        } else if (JavaKeywordConstant.EQUALS.equals(methodName) && paramTypes.length == 1) {
            return this.equals(args[0]);
        }
        Request request = new Request();
        ConsumerConfig consumerConfig = commonConfig.getConsumerConfig();
        BaseConfig baseConfig = commonConfig.getBaseConfig();
        buildSendRequest(args, buildRequest, methodName, paramTypeStrs, request, consumerConfig, baseConfig);
        // 业务并发调用
        Response response = waitResponse(request, baseConfig.getFaultTolerantType());
        SimpleRpcLog.info("返回值：===> {}", JSON.toJSONString(response));
        Object exceptionInfo = response.getExceptionInfo();
        if (!Objects.isNull(exceptionInfo)) {
            String s = exceptionInfo.toString();
            if (StrUtil.isNotBlank(s)) {
                throw new NettyInvokeException("remote invoke fail!");
            }
        }
        return response.getResult();
    }

    private Response waitResponse(Request request, String faultTolerantType) {
        // 容错机制
        MultiInvoker multiInvoker = new MultiInvoker(request, faultTolerantType);
        invokeThreadPool.submit(multiInvoker);
        Response response = null;
        int tryNum = Objects.isNull(request.getRetryNum()) || request.getRetryNum() <= 0 ? 100 : request.getRetryNum();
        for (int i = 0; i < tryNum; i++) {
            if (null != response) {
                break;
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            response = multiInvoker.getResponse();
        }
        if (null == response) {
            throw new NettyInitException("服务端调用失败");
        }
        return response;
    }

    private void buildSendRequest(Object[] args, Request buildRequest, String methodName, List<String> paramTypeStrs, Request request, ConsumerConfig consumerConfig, BaseConfig baseConfig) {
        //设置参数
        request.setMethodName(methodName);
        request.setParamTypes(paramTypeStrs);
        request.setArgs(args);
        request.setBeanName(consumerConfig.getBeanName());
        request.setInterfaceName(consumerConfig.getInterfaceName());
        request.setChannel(Objects.requireNonNull(ConnectCache.getChannelFuture(buildRequest)).channel());
        request.setAlias(consumerConfig.getAlias());
        request.setSerializer(baseConfig.getSerializer());
        request.setRegister(baseConfig.getRegister());
        request.setCompressor(baseConfig.getCompressor());
        request.setTimeout(baseConfig.getTimeout());
    }
}
