package com.simple.rpc.core.register.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: rpc 注册中心的配置
 *
 * @author: WuChengXing
 * @create: 2022-04-19 19:32
 **/
@Component
@ConfigurationProperties("rpc.register.redis")
public class RedisRegisterProperties extends RegisterProperties {

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
}
