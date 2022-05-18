package com.simple.rpc.springboot;

import com.simple.rpc.common.util.SimpleRpcLog;
import com.simple.rpc.common.config.LocalAddressInfo;
import com.simple.rpc.common.config.SimpleRpcUrl;
import com.simple.rpc.core.network.cache.RegisterInfoCache;
import com.simple.rpc.core.network.message.Request;
import com.simple.rpc.core.network.server.RpcServerSocket;
import com.simple.rpc.core.register.RegisterCenterFactory;
import com.simple.rpc.springboot.config.BootBaseConfig;
import com.simple.rpc.springboot.config.BootRegisterConfig;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-05-04 18:44
 **/
@Component
public class ServerInitBeanPostProcessor implements BeanPostProcessor, Ordered {

    ExecutorService executorService = Executors.newFixedThreadPool(10);

    private static Boolean initFlag = true;

    @Resource
    private BootRegisterConfig bootRegisterConfig;

    @Resource
    private BootBaseConfig bootBaseConfig;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (initFlag) {
            init(bootRegisterConfig);
            initFlag = false;
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    public void init(BootRegisterConfig bootRegisterConfig) throws BeansException {
        SimpleRpcUrl simpleRpcUrl = SimpleRpcUrl.toSimpleRpcUrl(bootRegisterConfig);
        // 保存注册中心信息
        RegisterInfoCache.save(bootRegisterConfig);
        //启动注册中心
        RegisterCenterFactory.create(simpleRpcUrl.getType()).init(simpleRpcUrl);
        SimpleRpcLog.info("注册中心初始化：{}", bootRegisterConfig.getAddress());
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

    @Override
    public int getOrder() {
        return 0;
    }
}
