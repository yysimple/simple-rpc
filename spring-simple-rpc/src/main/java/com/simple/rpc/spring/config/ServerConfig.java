package com.simple.rpc.spring.config;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-04-22 17:45
 **/
public class ServerConfig {
    /**
     * 注册类型
     */
    protected String registerType;
    /**
     * 注册中心地址
     */
    protected String host;

    /**
     * 注册中心端口
     */
    protected int port;

    /**
     * 注册中心的密码
     */
    protected String password;

    /**
     * 超时时间
     */
    protected Long timeout;

    /**
     * 重试次数
     */
    protected Integer tryNum;

    public String getRegisterType() {
        return registerType;
    }

    public void setRegisterType(String registerType) {
        this.registerType = registerType;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getTimeout() {
        return timeout;
    }

    public void setTimeout(Long timeout) {
        this.timeout = timeout;
    }

    public Integer getTryNum() {
        return tryNum;
    }

    public void setTryNum(Integer tryNum) {
        this.tryNum = tryNum;
    }
}
