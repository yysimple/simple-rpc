package com.simple.rpc.test.core.provider;

import com.simple.rpc.common.config.ConfigManager;
import com.simple.rpc.common.config.LocalAddressInfo;
import com.simple.rpc.common.config.RegistryConfig;
import com.simple.rpc.common.config.SimpleRpcUrl;
import com.simple.rpc.common.cache.SimpleRpcServiceCache;
import com.simple.rpc.core.network.message.Request;
import com.simple.rpc.core.network.server.RpcServerSocket;
import com.simple.rpc.common.interfaces.RegisterCenter;
import com.simple.rpc.core.register.RegisterCenterFactory;
import com.simple.rpc.test.core.provider.impl.CoreHelloServiceImpl;

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
public class ProviderAppMain {

    static ExecutorService executorService = Executors.newFixedThreadPool(10);

    public static void main(String[] args) throws InterruptedException {
        // 初始化注册中心
        RegistryConfig registryConfig = ConfigManager.getInstant().getRegistryConfig();
        SimpleRpcUrl simpleRpcUrl = SimpleRpcUrl.toSimpleRpcUrl(registryConfig);
        RegisterCenter registerCenter = RegisterCenterFactory.create(simpleRpcUrl.getType());
        registerCenter.init(simpleRpcUrl);
        Request request = new Request();
        request.setRequestId(1001L);

        String interfaceName = "com.simple.rpc.test.common.core.service.CoreHelloService";
        request.setInterfaceName(interfaceName);
        request.setAlias("rpcProvider");
        // 初始化
        RpcServerSocket rpcServerSocket = new RpcServerSocket(new Request());
        executorService.submit(rpcServerSocket);
        while (!rpcServerSocket.isActiveSocketServer()) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException ignore) {
            }
        }
        request.setHost(LocalAddressInfo.LOCAL_HOST);
        request.setPort(LocalAddressInfo.PORT);
        System.out.println("服务端的地址和端口：" +  request.getHost() + "-" + request.getPort());
        registerCenter.register(Request.request2Register(request));
        SimpleRpcServiceCache.addService("rpcProvider", new CoreHelloServiceImpl());
    }
}
