package com.simple.rpc.core.filter;

import com.simple.rpc.common.interfaces.SimpleRpcFilter;
import com.simple.rpc.common.interfaces.entity.InvokeFilterInfo;
import com.simple.rpc.common.interfaces.entity.SimpleRpcContext;
import com.simple.rpc.core.network.message.Request;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 远程方法调用之前的拦截器
 *
 * @author: WuChengXing
 * @create: 2022-06-05 02:03
 **/
public interface RemoteInvokeBeforeFilter extends SimpleRpcFilter {

    /**
     * 调用前
     *
     * @param request
     * @return
     */
    SimpleRpcContext invokeBefore(SimpleRpcContext request);
}
