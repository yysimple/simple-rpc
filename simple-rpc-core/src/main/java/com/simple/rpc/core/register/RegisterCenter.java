package com.simple.rpc.core.register;

import com.simple.rpc.core.network.message.Request;
import com.simple.rpc.core.register.config.RegisterProperties;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-04-21 17:00
 **/
public interface RegisterCenter<T extends RegisterProperties> {

    /**
     * 初始化注册中心
     *
     * @param registerProperties
     */
    void init(T registerProperties);

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

}