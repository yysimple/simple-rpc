package com.simple.rpc.core.register.config;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 注册中心
 *
 * @author: WuChengXing
 * @create: 2022-04-19 19:35
 **/
public class RegisterProperties {

    /**
     * 注册中心类型
     */
    private String registerType;

    /**
     * ip地址
     */
    private String address;

    /**
     * 端口
     */
    private Integer port;

    public String getRegisterType() {
        return registerType;
    }

    public void setRegisterType(String registerType) {
        this.registerType = registerType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
