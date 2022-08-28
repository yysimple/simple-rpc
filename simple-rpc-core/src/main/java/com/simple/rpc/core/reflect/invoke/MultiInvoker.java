package com.simple.rpc.core.reflect.invoke;

import com.simple.rpc.common.spi.ExtensionLoader;
import com.simple.rpc.core.network.message.Request;
import com.simple.rpc.core.network.message.Response;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-08-28 17:03
 **/
public class MultiInvoker implements Runnable {

    private final Request request;
    private final String faultTolerantType;
    private Response response;

    public MultiInvoker(Request request, String faultTolerantType) {
        this.request = request;
        this.faultTolerantType = faultTolerantType;
    }

    @Override
    public void run() {
        // 容错机制
        FaultTolerantInvoker faultTolerantInvoker = ExtensionLoader.getLoader(FaultTolerantInvoker.class)
                .getExtension(faultTolerantType);
        this.response = faultTolerantInvoker.invoke(request);
    }

    public Response getResponse() {
        return response;
    }
}
