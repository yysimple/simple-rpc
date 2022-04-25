package com.simple.rpc.core.spring.xml.beans;

import com.alibaba.fastjson.JSON;
import com.simple.rpc.core.constant.enums.RegisterEnum;
import com.simple.rpc.core.exception.network.NettyInitException;
import com.simple.rpc.core.exception.spring.BeanNotFoundException;
import com.simple.rpc.core.network.client.RpcClientSocket;
import com.simple.rpc.core.network.message.Request;
import com.simple.rpc.core.reflect.RpcProxy;
import com.simple.rpc.core.register.RegisterCenter;
import com.simple.rpc.core.register.RegisterCenterFactory;
import com.simple.rpc.core.register.config.RegisterProperties;
import com.simple.rpc.core.spring.xml.config.ConsumerConfig;
import com.simple.rpc.core.spring.xml.transfer.BaseData;
import com.simple.rpc.core.spring.xml.transfer.DataMap;
import com.simple.rpc.core.util.ClassLoaderUtils;
import io.netty.channel.ChannelFuture;
import org.springframework.beans.BeanUtils;
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

    ExecutorService executorService = Executors.newFixedThreadPool(10);

    @Override
    public T getObject() throws BeanNotFoundException, NettyInitException, ClassNotFoundException {
        RegisterProperties registerProperties = DataMap.dataTransfer.get(DataMap.DATA_TRANSFER);
        BaseData baseData = DataMap.baseDataTransfer.get(DataMap.BASE_DATA_TRANSFER);
        // 构建请求参数
        Request request = new Request();
        request.setInterfaceName(interfaceName);
        request.setAlias(alias);
        if (Objects.isNull(registerProperties) || Objects.isNull(registerProperties.getRegisterType())) {
            registerProperties = new RegisterProperties();
            if (Objects.isNull(baseData) || Objects.isNull(baseData.getRegisterType())) {
                registerProperties.setRegisterType(RegisterEnum.REDIS.getRegisterType());
            } else {
                BeanUtils.copyProperties(baseData, registerProperties);
            }
        }
        if (Objects.isNull(registerProperties.getRegisterType())) {
            throw new BeanNotFoundException("消费者从注册中心获取数据失败");
        }
        RegisterCenter registerCenter = RegisterCenterFactory.create(registerProperties.getRegisterType());
        if (Objects.isNull(registerCenter)) {
            throw new BeanNotFoundException("注册中心未初始化");
        }
        //从redis获取链接
        String infoStr = registerCenter.get(request);
        request = JSON.parseObject(infoStr, Request.class);
        Integer calcTryNum = calcTryNum(baseData, tryNum);
        Long calcTimeout = calcTimeout(baseData, timeout);
        //获取通信channel
        if (null == channelFuture) {
            RpcClientSocket clientSocket = new RpcClientSocket(request.getHost(), request.getPort());
            executorService.submit(clientSocket);
            for (int i = 0; i < calcTryNum; i++) {
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
        request.setBeanName(beanName);
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

    private Integer calcTryNum(BaseData baseData, Integer tryNum) {
        if (!Objects.isNull(tryNum) && tryNum >= 0) {
            return tryNum;
        }
        if (!Objects.isNull(baseData) && !Objects.isNull(baseData.getTryNum()) && baseData.getTryNum() >= 0) {
            return baseData.getTryNum();
        }
        return 100;
    }

    private Long calcTimeout(BaseData baseData, Long timeout) {
        if (!Objects.isNull(timeout) && timeout >= 0) {
            return timeout;
        }
        if (!Objects.isNull(baseData) && !Objects.isNull(baseData.getTimeout()) && baseData.getTimeout() >= 0) {
            return baseData.getTimeout();
        }
        return 30L;
    }
}
