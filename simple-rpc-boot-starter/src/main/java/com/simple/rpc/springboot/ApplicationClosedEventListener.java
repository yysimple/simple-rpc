package com.simple.rpc.springboot;

import com.simple.rpc.common.util.SimpleRpcLog;
import com.simple.rpc.core.config.entity.LocalAddressInfo;
import com.simple.rpc.core.config.entity.SimpleRpcUrl;
import com.simple.rpc.core.network.cache.RegisterInfoCache;
import com.simple.rpc.core.network.cache.SimpleRpcServiceCache;
import com.simple.rpc.core.network.server.hook.HookEntity;
import com.simple.rpc.core.network.server.hook.ServerExitHook;
import com.simple.rpc.core.register.RegisterCenter;
import com.simple.rpc.core.register.RegisterCenterFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: springboot退出的时候监听
 *
 * @author: WuChengXing
 * @create: 2022-05-10 19:11
 **/
@Component
public class ApplicationClosedEventListener implements ApplicationListener<ContextClosedEvent> {

    @Override
    public void onApplicationEvent(ContextClosedEvent contextClosedEvent) {
        RegisterCenter registerCenter = RegisterCenterFactory.create(SimpleRpcUrl.toSimpleRpcUrl(RegisterInfoCache.getRegisterInfo()).getType());
        List<String> strings = SimpleRpcServiceCache.allKey();
        HookEntity hookEntity = new HookEntity();
        hookEntity.setRpcServiceNames(strings);
        hookEntity.setServerUrl(LocalAddressInfo.LOCAL_HOST);
        hookEntity.setServerPort(LocalAddressInfo.PORT);
        registerCenter.unregister(hookEntity);
    }
}
