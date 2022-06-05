package com.simple.rpc.common.interfaces;

import com.simple.rpc.common.annotation.SimpleRpcSPI;
import com.simple.rpc.common.interfaces.entity.InvokeFilterInfo;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 过滤器
 *
 * @author: WuChengXing
 * @create: 2022-06-04 23:49
 **/
@SimpleRpcSPI
public interface SimpleRpcFilter {

    /**
     * 拦截器
     *
     * @param invokeFilterInfo
     */
    void filter(InvokeFilterInfo invokeFilterInfo);
}
