package com.simple.rpc.core.spring.xml.beans;

import com.alibaba.fastjson.JSON;
import com.simple.rpc.core.constant.enums.RegisterEnum;
import com.simple.rpc.core.network.client.RpcClientSocket;
import com.simple.rpc.core.network.message.Request;
import com.simple.rpc.core.reflect.RpcProxy;
import com.simple.rpc.core.register.RegisterCenter;
import com.simple.rpc.core.register.RegisterCenterFactory;
import com.simple.rpc.core.spring.xml.config.ConsumerConfig;
import com.simple.rpc.core.spring.xml.transfer.BaseData;
import com.simple.rpc.core.spring.xml.transfer.DataMap;
import com.simple.rpc.core.util.ClassLoaderUtils;
import io.netty.channel.ChannelFuture;
import org.springframework.beans.factory.FactoryBean;

import java.util.Objects;
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

    BaseData baseData = DataMap.baseDataTransfer.get(DataMap.BASE_DATA_TRANSFER);

    ExecutorService executorService = Executors.newFixedThreadPool(10);

    @Override
    public T getObject() throws Exception {

        // 构建请求参数
        Request request = new Request();
        request.setInterfaceName(interfaceName);
        request.setAlias(alias);

        RegisterCenter registerCenter = RegisterCenterFactory.create(RegisterEnum.REDIS.getRegisterType());
        //从redis获取链接
        assert registerCenter != null;
        String infoStr = registerCenter.get(request);
        request = JSON.parseObject(infoStr, Request.class);
        Integer calcTryNum = calcTryNum(tryNum);
        Long calcTimeout = calcTimeout(timeout);
        //获取通信channel
        if (null == channelFuture) {
            RpcClientSocket clientSocket = new RpcClientSocket(request.getHost(), request.getPort());
            executorService.submit(clientSocket);
            for (int i = 0; i < calcTryNum; i++) {
                if (null != channelFuture) {
                    break;
                }
                Thread.sleep(500);
                channelFuture = clientSocket.getFuture();
            }
        }

        request.setChannel(channelFuture.channel());
        request.setBeanName(request.getBeanName());
        request.setAlias(alias);
        request.setTimeout(calcTimeout);
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

    private Integer calcTryNum(Integer tryNum) {
        if (tryNum >= 0) {
            return tryNum;
        }
        if (!Objects.isNull(baseData) && baseData.getTryNum() >= 0) {
            return baseData.getTryNum();
        }
        return 10;
    }

    private Long calcTimeout(Long timeout) {
        if (timeout >= 0) {
            return timeout;
        }
        if (!Objects.isNull(baseData) && baseData.getTimeout() >= 0) {
            return baseData.getTimeout();
        }
        return 30L;
    }
}
