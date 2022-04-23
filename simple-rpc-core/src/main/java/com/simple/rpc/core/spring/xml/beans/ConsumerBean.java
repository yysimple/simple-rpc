package com.simple.rpc.core.spring.xml.beans;

import com.alibaba.fastjson.JSON;
import com.simple.rpc.core.constant.enums.RegisterEnum;
import com.simple.rpc.core.network.client.RpcClientSocket;
import com.simple.rpc.core.network.message.Request;
import com.simple.rpc.core.reflect.RpcProxy;
import com.simple.rpc.core.register.RegisterCenter;
import com.simple.rpc.core.register.RegisterCenterFactory;
import com.simple.rpc.core.spring.xml.config.ConsumerConfig;
import com.simple.rpc.core.util.ClassLoaderUtils;
import io.netty.channel.ChannelFuture;
import org.springframework.beans.factory.FactoryBean;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 服务消费者
 *
 * @author: WuChengXing
 * @create: 2022-04-22 22:01
 **/
public class ConsumerBean<T> extends ConsumerConfig implements FactoryBean<T> {

    private ChannelFuture channelFuture;

    ExecutorService executorService = Executors.newFixedThreadPool(10);

    @Override
    public T getObject() throws Exception {

        // 构建请求参数
        Request request = new Request();
        request.setInterfaceName(interfaceName);
        request.setAlias(alias);

        RegisterCenter registerCenter = RegisterCenterFactory.create(RegisterEnum.REDIS.getRegisterType());
        //从redis获取链接
        String infoStr = registerCenter.get(request);
        request = JSON.parseObject(infoStr, Request.class);

        //获取通信channel
        if (null == channelFuture) {
            RpcClientSocket clientSocket = new RpcClientSocket(request.getHost(), request.getPort());
            executorService.submit(clientSocket);
            for (int i = 0; i < 100; i++) {
                if (null != channelFuture) {
                    break;
                }
                Thread.sleep(500);
                channelFuture = clientSocket.getFuture();
            }
        }

        request.setChannel(channelFuture.channel());
        request.setRef(request.getRef());
        request.setAlias(alias);
        return (T) RpcProxy.invoke(ClassLoaderUtils.forName(interfaceName), request);
    }

    @Override
    public Class<?> getObjectType() {
        try {
            return ClassLoaderUtils.forName(interfaceName);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
