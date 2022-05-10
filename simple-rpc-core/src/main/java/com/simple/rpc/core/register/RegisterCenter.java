package com.simple.rpc.core.register;

import com.simple.rpc.core.config.entity.SimpleRpcUrl;
import com.simple.rpc.core.network.message.Request;
import com.simple.rpc.core.network.server.hook.HookEntity;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-04-21 17:00
 **/
public interface RegisterCenter {

    /**
     * 初始化注册中心
     *
     * @param url
     */
    void init(SimpleRpcUrl url);

    /**
     * 服务注册
     *
     * @param request
     * @return
     */
    Boolean register(Request request);

    /**
     * 获取服务
     *
     * @param request
     * @return
     */
    String get(Request request);

    /**
     * 注销服务
     *
     * @param hookEntity
     * @return
     */
    Boolean unregister(HookEntity hookEntity);

}
