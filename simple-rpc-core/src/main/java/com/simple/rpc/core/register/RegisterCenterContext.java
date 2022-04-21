package com.simple.rpc.core.register;

import com.simple.rpc.core.network.message.Request;
import com.simple.rpc.core.register.config.RegisterProperties;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 注册中心上下文
 *
 * @author: WuChengXing
 * @create: 2022-04-21 17:03
 **/
public class RegisterCenterContext<T extends RegisterProperties> {
    private final RegisterCenter<T> registerCenter;

    public RegisterCenterContext(RegisterCenter<T> registerCenter) {
        this.registerCenter = registerCenter;
    }

    public void init(T registerProperties) {
        registerCenter.init(registerProperties);
    }

    public void register(Request request) {
        registerCenter.register(request);
    }

    public void get(Request request) {
        registerCenter.get(request);
    }
}
