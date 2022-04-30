package com.simple.rpc.spring;

import com.alibaba.fastjson.JSON;
import com.simple.rpc.core.config.entity.LocalAddressInfo;
import com.simple.rpc.core.network.server.RpcServerSocket;
import com.simple.rpc.core.register.RegisterCenter;
import com.simple.rpc.core.register.RegisterCenterFactory;
import com.simple.rpc.core.register.config.RegisterProperties;
import com.simple.rpc.core.util.StringUtils;
import com.simple.rpc.spring.transfer.DataMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-04-29 15:30
 **/
@Configuration
@ConditionalOnClass({RegisterProperties.class})
@EnableConfigurationProperties({RegisterProperties.class})
public class ServerAutoConfiguration implements ApplicationContextAware {

    private Logger logger = LoggerFactory.getLogger(ServerAutoConfiguration.class);

    @Resource
    private RegisterProperties registerProperties;

    ExecutorService executorService = Executors.newFixedThreadPool(10);

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (Objects.isNull(registerProperties) || StringUtils.isEmpty(registerProperties.getRegisterType())) {
            registerProperties = DataMap.dataTransfer.get(DataMap.DATA_TRANSFER);
        }
        RegisterCenter registerCenter = RegisterCenterFactory.create(registerProperties.getRegisterType());
        logger.info("启动Redis模拟注册中心开始");
        registerCenter.init(registerProperties);
        logger.info("启动Redis模拟注册中心完成 {}", JSON.toJSON(registerProperties));

        logger.info("初始化生产端服务开始");
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
