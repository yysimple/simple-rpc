package com.simple.rpc.core.reflect;

import com.alibaba.fastjson.JSON;
import com.simple.rpc.core.config.entity.*;
import com.simple.rpc.core.constant.JavaKeywordConstant;
import com.simple.rpc.core.exception.SimpleRpcBaseException;
import com.simple.rpc.core.exception.network.NettyInitException;
import com.simple.rpc.core.network.cache.ConnectCache;
import com.simple.rpc.core.network.client.RpcClientSocket;
import com.simple.rpc.core.network.message.Request;
import com.simple.rpc.core.network.message.Response;
import com.simple.rpc.core.network.send.SyncWrite;
import com.simple.rpc.core.register.RegisterCenter;
import com.simple.rpc.core.register.RegisterCenterFactory;
import com.simple.rpc.core.util.SimpleRpcLog;
import io.netty.channel.ChannelFuture;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
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

    private ChannelFuture channelFuture;
    private final CommonConfig commonConfig;

    ExecutorService executorService = Executors.newFixedThreadPool(10);

    public RpcInvocationHandler(CommonConfig commonConfig) {
        this.commonConfig = commonConfig;
    }

    private void connect(Request request) {
        channelFuture = ConnectCache.getChannelFuture(request);
        if (this.channelFuture != null && this.channelFuture.channel().isOpen()) {
            return;
        }
        synchronized (this) {
            if (this.channelFuture != null && this.channelFuture.channel().isOpen()) {
                return;
            }
            //获取通信channel
            if (null == this.channelFuture) {
                RpcClientSocket clientSocket = new RpcClientSocket(request.getHost(), request.getPort());
                executorService.submit(clientSocket);
                for (int i = 0; i < 100; i++) {
                    if (null != this.channelFuture) {
                        break;
                    }
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    this.channelFuture = clientSocket.getFuture();
                }
            }
            if (null == this.channelFuture) {
                throw new NettyInitException("客户端未连接上服务端，考虑增加重试次数");
            }
            request.setChannelFuture(channelFuture);
            ConnectCache.saveChannelFuture(request);
        }
    }

    private Request buildRequest() {
        ConsumerConfig consumerConfig = commonConfig.getConsumerConfig();
        RegistryConfig registryConfig = commonConfig.getRegistryConfig();
        BaseConfig baseConfig = commonConfig.getBaseConfig();
        // 构建请求参数
        Request request = new Request();
        request.setInterfaceName(consumerConfig.getInterfaceName());
        request.setAlias(consumerConfig.getAlias());
        request.setLoadBalanceRule(baseConfig.getLoadBalanceRule());
        SimpleRpcUrl simpleRpcUrl = SimpleRpcUrl.toSimpleRpcUrl(registryConfig);
        RegisterCenter registerCenter = RegisterCenterFactory.create(simpleRpcUrl.getType());
        if (Objects.isNull(registerCenter)) {
            throw new SimpleRpcBaseException("注册中心未初始化");
        }
        //从redis获取链接
        String infoStr = registerCenter.get(request);
        request = JSON.parseObject(infoStr, Request.class);
        return request;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 连接客户端
        connect(buildRequest());
        String methodName = method.getName();
        Class[] paramTypes = method.getParameterTypes();
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
        //设置参数
        request.setMethodName(methodName);
        request.setParamTypes(paramTypes);
        request.setArgs(args);
        request.setBeanName(consumerConfig.getBeanName());
        request.setInterfaceName(consumerConfig.getInterfaceName());
        request.setChannel(channelFuture.channel());
        request.setAlias(consumerConfig.getAlias());
        request.setSerializer(baseConfig.getSerializer());
        request.setRegister(baseConfig.getRegister());
        request.setCompressor(baseConfig.getCompressor());
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
