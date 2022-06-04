package com.simple.rpc.core.network.message;

import com.simple.rpc.common.interfaces.entity.RegisterInfo;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import lombok.Data;

import java.util.List;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 请求消息体
 *
 * @author: WuChengXing
 * @create: 2022-04-18 18:53
 **/
@Data
public class Request {

    /**
     * 绑定此次连接的channel
     */
    private transient Channel channel;
    private transient ChannelFuture channelFuture;

    /**
     * 此次请求的id
     */
    private long requestId;

    /**
     * 方法
     */
    private String methodName;

    /**
     * 属性
     */
    private List<String> paramTypes;

    /**
     * 入参
     */
    private Object[] args;

    /**
     * 接口
     */
    private String interfaceName;

    /**
     * 实现类对应的beanName
     */
    private String beanName;

    /**
     * 别名
     */
    private String alias;

    /**
     * 设置超时时间
     */
    private Long timeout;

    /**
     * 重试次数
     */
    private Integer retryNum;

    /**
     * nettyServer端的host
     */
    private String host;

    /**
     * nettyServer端的port
     */
    private Integer port;

    /**
     * 负载均衡算法
     */
    private String loadBalanceRule;

    /**
     * 权重算法的时候的权重值
     */
    private Integer weights;

    /**
     * 序列化的SPI类型
     */
    private String serializer;

    /**
     * 压缩的SPI类型
     */
    private String compressor;

    /**
     * 注册中心的SPI类型
     */
    private String register;

    /**
     * 心跳间隔时间
     */
    private Long beatIntervalTime;

    /**
     * 客户端停止心跳或者写操作后，服务端断开时间
     */
    private Long stopConnectTime;

    public static Request register2Request(RegisterInfo info) {
        Request request = new Request();
        request.setHost(info.getHost());
        request.setPort(info.getPort());
        request.setInterfaceName(info.getInterfaceName());
        request.setTimeout(info.getTimeout());
        request.setRetryNum(info.getRetryNum());
        request.setBeanName(info.getBeanName());
        request.setAlias(info.getAlias());
        request.setLoadBalanceRule(info.getLoadBalanceRule());
        request.setSerializer(info.getSerializer());
        request.setCompressor(info.getCompressor());
        request.setRegister(info.getRegister());
        request.setWeights(info.getWeights());
        return request;
    }

    public static RegisterInfo request2Register(Request request) {
        RegisterInfo registerInfo = new RegisterInfo();
        registerInfo.setInterfaceName(request.getInterfaceName());
        registerInfo.setBeanName(request.getBeanName());
        registerInfo.setAlias(request.getAlias());
        registerInfo.setTimeout(request.getTimeout());
        registerInfo.setRetryNum(request.getRetryNum());
        registerInfo.setHost(request.getHost());
        registerInfo.setPort(request.getPort());
        registerInfo.setLoadBalanceRule(request.getLoadBalanceRule());
        registerInfo.setWeights(request.getWeights());
        registerInfo.setSerializer(request.getSerializer());
        registerInfo.setCompressor(request.getCompressor());
        registerInfo.setRegister(request.getRegister());
        return registerInfo;
    }
}
