package com.simple.rpc.common.config;

import com.simple.rpc.common.annotation.SimpleRpcConfig;
import lombok.Data;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 注册中心的配置
 *
 * @author: WuChengXing
 * @create: 2022-04-30 10:50
 **/
@Data
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
}
