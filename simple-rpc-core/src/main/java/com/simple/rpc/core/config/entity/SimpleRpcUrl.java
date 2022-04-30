package com.simple.rpc.core.config.entity;

import com.simple.rpc.core.exception.SimpleRpcBaseException;

import java.util.Objects;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: url信息
 *
 * @author: WuChengXing
 * @create: 2022-04-30 10:55
 **/
public class SimpleRpcUrl {

    private String type;

    private String host;

    private Integer port;

    private String username;

    private String password;

    private String database;

    private String table;

    public static SimpleRpcUrl toSimpleRpcUrl(RegistryConfig config) {
        if (Objects.isNull(config)) {
            throw new SimpleRpcBaseException("为获取到注册中心配置");
        }
        // 构建url其他参数
        SimpleRpcUrl simpleRpcUrl = buildSimpleRpcUrl(config);
        parseUrl(simpleRpcUrl, config);
        return simpleRpcUrl;
    }

    private static void parseUrl(SimpleRpcUrl simpleRpcUrl, RegistryConfig config) {
        String address = config.getAddress();
        String[] split = address.split("://");
        if (split.length < 1) {
            throw new SimpleRpcBaseException("不支持该注册中心地址格式");
        }
        simpleRpcUrl.setType(split[0]);
        String[] infos = split[1].split(":");
        if (infos.length < 1) {
            throw new SimpleRpcBaseException("不支持该注册中心地址格式");
        }
        simpleRpcUrl.setHost(infos[0]);
        simpleRpcUrl.setPort(Integer.valueOf(infos[1]));
    }

    private static SimpleRpcUrl buildSimpleRpcUrl(RegistryConfig config) {
        SimpleRpcUrl simpleRpcUrl = new SimpleRpcUrl();
        simpleRpcUrl.setUsername(config.getUsername());
        simpleRpcUrl.setPassword(config.getPassword());
        simpleRpcUrl.setDatabase(config.getDatabase());
        simpleRpcUrl.setTable(config.getTable());
        return simpleRpcUrl;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
        return "SimpleRpcUrl{" +
                "type='" + type + '\'' +
                ", host='" + host + '\'' +
                ", port=" + port +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", database='" + database + '\'' +
                ", table='" + table + '\'' +
                '}';
    }
}
