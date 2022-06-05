package com.simple.rpc.core.filter;

import com.simple.rpc.common.interfaces.SimpleRpcFilter;
import com.simple.rpc.core.network.message.Response;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-06-05 02:02
 **/
public interface InvokeAfterFilter extends SimpleRpcFilter {

    /**
     * 调用后
     *
     * @param response
     * @return
     */
    Response invokeAfter(Response response);
}
