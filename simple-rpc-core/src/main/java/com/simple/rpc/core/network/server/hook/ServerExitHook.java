package com.simple.rpc.core.network.server.hook;

import com.simple.rpc.common.util.SimpleRpcLog;
import com.simple.rpc.core.config.entity.LocalAddressInfo;
import com.simple.rpc.core.register.RegisterCenter;
import com.simple.rpc.core.register.RegisterCenterFactory;

import java.util.List;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: server端的退出
 *
 * @author: WuChengXing
 * @create: 2022-05-10 11:28
 **/
public class ServerExitHook {
    public static void addShutdownHook(HookEntity hookEntity) {
        SimpleRpcLog.info("addShutdownHook for clearAll");
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            RegisterCenter registry = RegisterCenterFactory.create(hookEntity.getRegisterType());
            SimpleRpcLog.info("注销服务的端地址+端口 {} {}", LocalAddressInfo.LOCAL_HOST, LocalAddressInfo.PORT);
            SimpleRpcLog.warn("注销该服务");
        }));
    }
}
