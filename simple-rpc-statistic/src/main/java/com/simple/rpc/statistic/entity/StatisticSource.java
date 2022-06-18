package com.simple.rpc.statistic.entity;

import com.simple.rpc.common.annotation.SimpleRpcConfig;
import lombok.Data;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-06-18 18:16
 **/
@Data
@SimpleRpcConfig(prefix = "simple.rpc.statistic")
public class StatisticSource {

    /**
     * 这里的定义规定：redis://127.0.0.1:6379 mysql://127.0.0.1:3306 zk://127.0.0.1:2181等
     */
    private String mysqlAddress;

    private String username;

    private String password;

    private String database;

    private String table;

    private String redisAddress;
    private String redisPassword;
}
