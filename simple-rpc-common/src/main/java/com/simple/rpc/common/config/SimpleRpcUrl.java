package com.simple.rpc.common.config;


import com.simple.rpc.common.exception.SimpleRpcBaseException;
import lombok.Data;

import java.util.Objects;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: url信息
 *
 * @author: WuChengXing
 * @create: 2022-04-30 10:55
 **/
@Data
public class SimpleRpcUrl {

    private String type;

    private String host;

    private Integer port;

    private String username;

    private String password;

    private String database;

    private String table;

    public static <T extends RegistryConfig> SimpleRpcUrl toSimpleRpcUrl(T config) {
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
}
