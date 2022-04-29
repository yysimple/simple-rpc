package com.simple.rpc.spring.beans;

import com.simple.rpc.core.config.LocalAddressInfo;
import com.simple.rpc.core.network.server.RpcServerSocket;
import com.simple.rpc.core.register.RegisterCenterFactory;
import com.simple.rpc.core.register.config.RegisterProperties;
import com.simple.rpc.core.spring.xml.config.ServerConfig;
import com.simple.rpc.core.spring.xml.transfer.BaseData;
import com.simple.rpc.core.spring.xml.transfer.DataMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger logger = LoggerFactory.getLogger(ServerBean.class);

    ExecutorService executorService = Executors.newFixedThreadPool(10);

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        RegisterProperties registerProperties = new RegisterProperties();
        registerProperties.setRegisterType(registerType);
        registerProperties.setHost(host);
        registerProperties.setPort(port);
        registerProperties.setPassword(password);
        DataMap.dataTransfer.put(DataMap.DATA_TRANSFER, registerProperties);
        // 构建全局数据
        BaseData baseData = new BaseData();
        baseData.setTimeout(timeout);
        baseData.setTryNum(tryNum);
        baseData.setRegisterType(registerType);
        DataMap.baseDataTransfer.put(DataMap.BASE_DATA_TRANSFER, baseData);
        //启动注册中心
        logger.info("启动注册中心 ...");
        RegisterCenterFactory.create(registerType).init(registerProperties);
        logger.info("启动注册中心完成 {} {}", host, port);

        //初始化服务端
        logger.info("初始化生产端服务 ...");
        RpcServerSocket serverSocket = new RpcServerSocket(applicationContext);
        executorService.submit(serverSocket);
        while (!serverSocket.isActiveSocketServer()) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException ignore) {
            }
        }
        logger.info("初始化生产端服务完成 {} {}", LocalAddressInfo.LOCAL_HOST, LocalAddressInfo.PORT);
    }
}
