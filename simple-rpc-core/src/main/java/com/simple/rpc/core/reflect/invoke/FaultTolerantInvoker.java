package com.simple.rpc.core.reflect.invoke;

import com.simple.rpc.common.annotation.SimpleRpcSPI;
import com.simple.rpc.core.network.message.Request;
import com.simple.rpc.core.network.message.Response;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 容错invoker
 *
 * @author: WuChengXing
 * @create: 2022-06-04 18:44
 **/
@SimpleRpcSPI("fast-fail")
public interface FaultTolerantInvoker {

    /**
     * 远程调用
     *
     * @param request
     * @return
     */
    Response invoke(Request request);
}
