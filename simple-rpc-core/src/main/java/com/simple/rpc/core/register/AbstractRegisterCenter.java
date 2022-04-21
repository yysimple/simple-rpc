package com.simple.rpc.core.register;

import com.simple.rpc.core.network.message.Request;
import com.simple.rpc.core.register.config.RegisterProperties;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 注册中心公共抽象类
 *
 * @author: WuChengXing
 * @create: 2022-04-21 18:44
 **/
public abstract class AbstractRegisterCenter<T extends RegisterProperties> implements RegisterCenter<T> {

    @Override
    public void init(T registerProperties) {

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
