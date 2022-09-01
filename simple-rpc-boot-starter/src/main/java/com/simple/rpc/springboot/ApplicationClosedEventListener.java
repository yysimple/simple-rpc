package com.simple.rpc.springboot;

import cn.hutool.core.collection.CollectionUtil;
import com.simple.rpc.common.cache.ApplicationCache;
import com.simple.rpc.common.cache.RegisterInfoCache;
import com.simple.rpc.common.cache.SimpleRpcServiceCache;
import com.simple.rpc.common.config.LocalAddressInfo;
import com.simple.rpc.common.config.SimpleRpcUrl;
import com.simple.rpc.common.constant.SymbolConstant;
import com.simple.rpc.common.interfaces.RegisterCenter;
import com.simple.rpc.common.network.HookEntity;
import com.simple.rpc.core.network.send.SyncWriteMap;
import com.simple.rpc.core.network.send.WriteFuture;
import com.simple.rpc.core.register.RegisterCenterFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: springboot退出的时候监听
 *
 * @author: WuChengXing
 * @create: 2022-05-10 19:11
 **/
public class ApplicationClosedEventListener implements ApplicationListener<ContextClosedEvent> {

    private final static Logger logger = LoggerFactory.getLogger(ApplicationClosedEventListener.class);

    @Override
    public void onApplicationEvent(ContextClosedEvent contextClosedEvent) {
        String url = LocalAddressInfo.LOCAL_HOST + SymbolConstant.UNDERLINE + LocalAddressInfo.PORT;
        RegisterCenter registerCenter = RegisterCenterFactory.create(SimpleRpcUrl.toSimpleRpcUrl(RegisterInfoCache.getRegisterInfo(url)).getType());
        // 服务暂停对外
        registerCenter.offline();
        // 优雅下线
        graceShutdown();
        List<String> strings = SimpleRpcServiceCache.allKey();
        HookEntity hookEntity = new HookEntity();
        hookEntity.setRpcServiceNames(strings);
        hookEntity.setServerUrl(LocalAddressInfo.LOCAL_HOST);
        hookEntity.setServerPort(LocalAddressInfo.PORT);
        hookEntity.setApplicationName(ApplicationCache.APPLICATION_NAME);
        registerCenter.unregister(hookEntity);

        List<String> urls = new ArrayList<>();
        urls.add(url);
        // 移除注册信息
        RegisterInfoCache.remove(urls);
    }

    private void graceShutdown() {
        // 拿到目前所有的请求
        Map<Long, WriteFuture> CLIENT_REQUEST = SyncWriteMap.CLIENT_REQUEST;
        Map<Long, Thread> SERVER_REQUEST = SyncWriteMap.SERVER_REQUEST;
        logger.error("优雅下线开始,当前client请求数：【{}】，server请求数【{}】", CLIENT_REQUEST.size(), SERVER_REQUEST.size());
        // 拿到所有的请求id
        for (; ; ) {
            if (CollectionUtil.isEmpty(SERVER_REQUEST)) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return;
            }
        }
    }
}
