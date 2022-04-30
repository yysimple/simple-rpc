package com.simple.rpc.core.register;

import com.simple.rpc.core.config.entity.RegistryConfig;
import com.simple.rpc.core.config.entity.SimpleRpcUrl;
import com.simple.rpc.core.network.message.Request;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 注册中心公共抽象类
 *
 * @author: WuChengXing
 * @create: 2022-04-21 18:44
 **/
public abstract class AbstractRegisterCenter implements RegisterCenter {

    @Override
    public void init(SimpleRpcUrl url) {

    }

    @Override
    public Boolean register(Request request) {
        return null;
    }

    @Override
    public String get(Request request) {
        return null;
    }
}
