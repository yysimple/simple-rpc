package com.simple.rpc.test.core.consumer;

import com.alibaba.fastjson.JSON;
import com.simple.rpc.common.exception.network.NettyInitException;
import com.simple.rpc.common.config.ConfigManager;
import com.simple.rpc.common.config.RegistryConfig;
import com.simple.rpc.common.config.SimpleRpcUrl;
import com.simple.rpc.core.network.client.RpcClientSocket;
import com.simple.rpc.core.network.message.Request;
import com.simple.rpc.common.interfaces.RegisterCenter;
import com.simple.rpc.core.register.RegisterCenterFactory;
import io.netty.channel.ChannelFuture;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-04-30 12:12
 **/
public class ConsumerAppMain {

    static ExecutorService executorService = Executors.newFixedThreadPool(10);

    public static void main(String[] args) throws ClassNotFoundException {
        // 初始化注册中心
        RegistryConfig registryConfig = ConfigManager.getInstant().getRegistryConfig();
        SimpleRpcUrl simpleRpcUrl = SimpleRpcUrl.toSimpleRpcUrl(registryConfig);
        RegisterCenter registerCenter = RegisterCenterFactory.create(simpleRpcUrl.getType());
        registerCenter.init(simpleRpcUrl);

        Request request = new Request();
        String interfaceName = "com.simple.rpc.test.common.core.service.CoreHelloService";
        request.setInterfaceName(interfaceName);
        request.setAlias("rpcProvider");
        request.setRequestId(1001L);
        String infoStr = registerCenter.get(Request.request2Register(request));
        request = JSON.parseObject(infoStr, Request.class);
        ChannelFuture channelFuture = null;
        System.out.println("服务端的地址和端口：" +  request.getHost() + "-" + request.getPort());
        //获取通信channel
        if (null == channelFuture) {
            RpcClientSocket clientSocket = new RpcClientSocket(request);
            executorService.submit(clientSocket);
            for (int i = 0; i < 100; i++) {
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
        request.setChannel(channelFuture.channel());
//        Object invoke = RpcProxy.invoke(ClassLoaderUtils.forName(interfaceName), request);
//        System.out.println(invoke);
    }
}
