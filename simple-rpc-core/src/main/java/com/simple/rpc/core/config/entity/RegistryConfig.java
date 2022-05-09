package com.simple.rpc.core.config.entity;

import com.simple.rpc.common.annotation.SimpleRpcConfig;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 注册中心的配置
 *
 * @author: WuChengXing
 * @create: 2022-04-30 10:50
 **/
@SimpleRpcConfig(prefix = "simple.rpc.register")
public class RegistryConfig {

    /**
     * 这里的定义规定：redis://127.0.0.1:6379 mysql://127.0.0.1:3306 zk://127.0.0.1:2181等
     */
    private String address;

    private String username;

    private String password;

    private String database;

    private String table;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    @Override
    public String toString() {
        return "RegistryConfig{" +
                "address='" + address + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", database='" + database + '\'' +
                ", table='" + table + '\'' +
                '}';
    }
}
