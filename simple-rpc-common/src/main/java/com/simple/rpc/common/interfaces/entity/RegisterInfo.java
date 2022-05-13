package com.simple.rpc.common.interfaces.entity;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 注册信息
 *
 * @author: WuChengXing
 * @create: 2022-05-13 16:54
 **/
public class RegisterInfo {

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

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
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

    public String getLoadBalanceRule() {
        return loadBalanceRule;
    }

    public void setLoadBalanceRule(String loadBalanceRule) {
        this.loadBalanceRule = loadBalanceRule;
    }

    public Integer getWeights() {
        return weights;
    }

    public void setWeights(Integer weights) {
        this.weights = weights;
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
}
