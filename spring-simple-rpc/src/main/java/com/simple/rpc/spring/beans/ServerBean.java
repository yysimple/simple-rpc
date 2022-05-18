package com.simple.rpc.spring.beans;

import com.simple.rpc.common.util.SimpleRpcLog;
import com.simple.rpc.common.config.LocalAddressInfo;
import com.simple.rpc.common.config.ServerConfig;
import com.simple.rpc.common.config.SimpleRpcUrl;
import com.simple.rpc.core.network.message.Request;
import com.simple.rpc.core.network.server.RpcServerSocket;
import com.simple.rpc.core.register.RegisterCenterFactory;
import com.simple.rpc.spring.beans.parser.ParseServerBean;
import com.simple.rpc.spring.transfer.DataMap;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-04-22 22:03
 **/
public class ServerBean extends ServerConfig implements ApplicationContextAware {

    ExecutorService executorService = Executors.newFixedThreadPool(10);

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SimpleRpcUrl simpleRpcUrl = ParseServerBean.parse(this);
        DataMap.DATA_TRANSFER_MAP.put(DataMap.DATA_TRANSFER, simpleRpcUrl);
        //启动注册中心
        RegisterCenterFactory.create(simpleRpcUrl.getType()).init(simpleRpcUrl);
        SimpleRpcLog.info("注册中心初始化：{}", address);
        //初始化服务端
        RpcServerSocket serverSocket = new RpcServerSocket(new Request());
        executorService.submit(serverSocket);
        while (!serverSocket.isActiveSocketServer()) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException ignore) {
            }
        }
        SimpleRpcLog.info("初始化生产端服务完成 {} {}", LocalAddressInfo.LOCAL_HOST, LocalAddressInfo.PORT);
    }
}
