package com.simple.rpc.springboot;

import com.simple.rpc.common.config.LocalAddressInfo;
import com.simple.rpc.common.config.SimpleRpcUrl;
import com.simple.rpc.common.constant.SymbolConstant;
import com.simple.rpc.core.network.cache.ConnectCache;
import com.simple.rpc.core.network.cache.RegisterInfoCache;
import com.simple.rpc.core.network.cache.SimpleRpcServiceCache;
import com.simple.rpc.common.network.HookEntity;
import com.simple.rpc.common.interfaces.RegisterCenter;
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
