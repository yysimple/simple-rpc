package com.simple.rpc.core.network.server.hook;

import com.simple.rpc.common.util.SimpleRpcLog;
import com.simple.rpc.core.config.entity.LocalAddressInfo;
import com.simple.rpc.core.config.entity.SimpleRpcUrl;
import com.simple.rpc.core.network.cache.RegisterInfoCache;
import com.simple.rpc.core.network.cache.SimpleRpcServiceCache;
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
    public static void addShutdownHook() {
        SimpleRpcLog.info("addShutdownHook for clearAll");
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            RegisterCenter registerCenter = RegisterCenterFactory.create(SimpleRpcUrl.toSimpleRpcUrl(RegisterInfoCache.getRegisterInfo()).getType());
            List<String> strings = SimpleRpcServiceCache.allKey();
            HookEntity hookEntity = new HookEntity();
            hookEntity.setRpcServiceNames(strings);
            hookEntity.setServerUrl(LocalAddressInfo.LOCAL_HOST);
            hookEntity.setServerPort(LocalAddressInfo.PORT);
            registerCenter.unregister(hookEntity);
            SimpleRpcLog.warn("退出服务");
        }));
    }
}
