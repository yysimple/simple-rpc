package com.simple.rpc.common.interfaces;


import com.simple.rpc.common.annotation.SimpleRpcSPI;
import com.simple.rpc.common.config.SimpleRpcUrl;
import com.simple.rpc.common.interfaces.entity.RegisterInfo;
import com.simple.rpc.common.network.HookEntity;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-04-21 17:00
 **/
@SimpleRpcSPI(value = "redis")
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
    String register(RegisterInfo request);

    /**
     * 获取服务
     *
     * @param request
     * @return
     */
    String get(RegisterInfo request);

    /**
     * 注销服务
     *
     * @param hookEntity
     * @return
     */
    Boolean unregister(HookEntity hookEntity);

}
