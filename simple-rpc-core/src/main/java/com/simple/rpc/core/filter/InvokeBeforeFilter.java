package com.simple.rpc.core.filter;

import com.simple.rpc.common.interfaces.SimpleRpcFilter;
import com.simple.rpc.common.interfaces.entity.SimpleRpcContext;
import com.simple.rpc.core.network.message.Request;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 方法调用之前的过滤器
 *
 * @author: WuChengXing
 * @create: 2022-06-05 01:49
 **/
public interface InvokeBeforeFilter extends SimpleRpcFilter {

    /**
     * 调用前
     *
     * @param context
     * @return
     */
    SimpleRpcContext invokeBefore(SimpleRpcContext context);
}
