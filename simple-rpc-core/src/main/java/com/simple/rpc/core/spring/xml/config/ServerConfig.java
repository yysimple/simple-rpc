package com.simple.rpc.core.spring.xml.config;

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
}
