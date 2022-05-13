package com.simple.rpc.spring.beans.parser;

import com.simple.rpc.common.config.RegistryConfig;
import com.simple.rpc.common.config.SimpleRpcUrl;
import com.simple.rpc.spring.beans.ServerBean;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-05-01 10:51
 **/
public class ParseServerBean {

    public static SimpleRpcUrl parse(ServerBean serverBean) {
        return SimpleRpcUrl.toSimpleRpcUrl(serverToRegister(serverBean));
    }

    public static RegistryConfig serverToRegister(ServerBean serverBean) {
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress(serverBean.getAddress());
        registryConfig.setUsername(serverBean.getUsername());
        registryConfig.setPassword(serverBean.getPassword());
        registryConfig.setDatabase(serverBean.getDatabase());
        registryConfig.setTable(serverBean.getTable());
        return registryConfig;
    }
}
