package com.simple.rpc.common.interfaces.entity;

import java.util.Arrays;
import java.util.List;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 过滤器实体
 *
 * @author: WuChengXing
 * @create: 2022-06-05 00:32
 **/
public class InvokeFilterInfo {
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

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public List<String> getParamTypes() {
        return paramTypes;
    }

    public void setParamTypes(List<String> paramTypes) {
        this.paramTypes = paramTypes;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public Long getTimeout() {
        return timeout;
    }

    public void setTimeout(Long timeout) {
        this.timeout = timeout;
    }

    public Integer getRetryNum() {
        return retryNum;
    }

    public void setRetryNum(Integer retryNum) {
        this.retryNum = retryNum;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getLoadBalanceRule() {
        return loadBalanceRule;
    }

    public void setLoadBalanceRule(String loadBalanceRule) {
        this.loadBalanceRule = loadBalanceRule;
    }

    public String getSerializer() {
        return serializer;
    }

    public void setSerializer(String serializer) {
        this.serializer = serializer;
    }

    public String getCompressor() {
        return compressor;
    }

    public void setCompressor(String compressor) {
        this.compressor = compressor;
    }

    public String getRegister() {
        return register;
    }

    public void setRegister(String register) {
        this.register = register;
    }

    public Integer getWeights() {
        return weights;
    }

    public void setWeights(Integer weights) {
        this.weights = weights;
    }

    public Long getBeatIntervalTime() {
        return beatIntervalTime;
    }

    public void setBeatIntervalTime(Long beatIntervalTime) {
        this.beatIntervalTime = beatIntervalTime;
    }

    public Long getStopConnectTime() {
        return stopConnectTime;
    }

    public void setStopConnectTime(Long stopConnectTime) {
        this.stopConnectTime = stopConnectTime;
    }

    public static InvokeFilterInfo register2Request(RegisterInfo info) {
        InvokeFilterInfo invokeFilterInfo = new InvokeFilterInfo();
        invokeFilterInfo.setHost(info.getHost());
        invokeFilterInfo.setPort(info.getPort());
        invokeFilterInfo.setInterfaceName(info.getInterfaceName());
        invokeFilterInfo.setTimeout(info.getTimeout());
        invokeFilterInfo.setRetryNum(info.getRetryNum());
        invokeFilterInfo.setBeanName(info.getBeanName());
        invokeFilterInfo.setAlias(info.getAlias());
        invokeFilterInfo.setLoadBalanceRule(info.getLoadBalanceRule());
        invokeFilterInfo.setSerializer(info.getSerializer());
        invokeFilterInfo.setCompressor(info.getCompressor());
        invokeFilterInfo.setRegister(info.getRegister());
        invokeFilterInfo.setWeights(info.getWeights());
        return invokeFilterInfo;
    }

    public static RegisterInfo request2Register(InvokeFilterInfo request) {
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

    @Override
    public String toString() {
        return "InvokeFilterInfo{" +
                "requestId=" + requestId +
                ", methodName='" + methodName + '\'' +
                ", paramTypes=" + paramTypes +
                ", args=" + Arrays.toString(args) +
                ", interfaceName='" + interfaceName + '\'' +
                ", beanName='" + beanName + '\'' +
                ", alias='" + alias + '\'' +
                ", timeout=" + timeout +
                ", retryNum=" + retryNum +
                ", host='" + host + '\'' +
                ", port=" + port +
                ", loadBalanceRule='" + loadBalanceRule + '\'' +
                ", weights=" + weights +
                ", serializer='" + serializer + '\'' +
                ", compressor='" + compressor + '\'' +
                ", register='" + register + '\'' +
                ", beatIntervalTime=" + beatIntervalTime +
                ", stopConnectTime=" + stopConnectTime +
                '}';
    }
}
