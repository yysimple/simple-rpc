package com.simple.rpc.core.register.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 注册中心
 *
 * @author: WuChengXing
 * @create: 2022-04-19 19:35
 **/
@Component
@ConfigurationProperties("simple.rpc.register")
public class RegisterProperties {

    /**
     * 注册中心类型
     */
    private String registerType;

    /**
     * ip地址
     */
    private String host;

    /**
     * 端口
     */
    private Integer port;

    /**
     * 连接密码
     */
    private String password;

    /**
     * 数据库编号
     */
    private Integer databaseNum;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getDatabaseNum() {
        return databaseNum;
    }

    public void setDatabaseNum(Integer databaseNum) {
        this.databaseNum = databaseNum;
    }

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

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
