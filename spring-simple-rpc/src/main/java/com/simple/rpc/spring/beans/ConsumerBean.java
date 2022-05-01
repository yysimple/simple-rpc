package com.simple.rpc.spring.beans;

import com.alibaba.fastjson.JSON;
import com.simple.rpc.core.config.entity.SimpleRpcUrl;
import com.simple.rpc.core.exception.network.NettyInitException;
import com.simple.rpc.core.network.client.RpcClientSocket;
import com.simple.rpc.core.network.message.Request;
import com.simple.rpc.core.reflect.RpcProxy;
import com.simple.rpc.core.register.RegisterCenter;
import com.simple.rpc.core.register.RegisterCenterFactory;
import com.simple.rpc.core.util.ClassLoaderUtils;
import com.simple.rpc.spring.beans.parser.ParseServerBean;
import com.simple.rpc.spring.config.ConsumerConfig;
import com.simple.rpc.spring.exception.BeanNotFoundException;
import com.simple.rpc.spring.transfer.BaseData;
import io.netty.channel.ChannelFuture;
import org.springframework.beans.factory.FactoryBean;

import javax.annotation.Resource;
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

    @Resource
    private ServerBean serverBean;

    @Override
    public T getObject() throws BeanNotFoundException, NettyInitException, ClassNotFoundException {
        // 构建请求参数
        Request request = new Request();
        request.setInterfaceName(interfaceName);
        request.setAlias(alias);
        SimpleRpcUrl simpleRpcUrl = ParseServerBean.parse(serverBean);
        RegisterCenter registerCenter = RegisterCenterFactory.create(simpleRpcUrl.getType());
        if (Objects.isNull(registerCenter)) {
            throw new BeanNotFoundException("注册中心未初始化");
        }
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
        request.setTimeout(null);
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
