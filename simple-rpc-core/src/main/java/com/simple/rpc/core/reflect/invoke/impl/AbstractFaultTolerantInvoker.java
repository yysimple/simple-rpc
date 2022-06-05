package com.simple.rpc.core.reflect.invoke.impl;

import cn.hutool.core.bean.BeanUtil;
import com.simple.rpc.core.filter.impl.FilterInvoke;
import com.simple.rpc.core.filter.impl.SpiLoadFilter;
import com.simple.rpc.core.network.message.Request;
import com.simple.rpc.core.network.message.Response;
import com.simple.rpc.core.reflect.invoke.FaultTolerantInvoker;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 抽象调用
 *
 * @author: WuChengXing
 * @create: 2022-06-05 02:25
 **/
public abstract class AbstractFaultTolerantInvoker implements FaultTolerantInvoker {

    @Override
    public Response invoke(Request request) {
        SpiLoadFilter.loadFilters();
        Request sendRequest = new Request();
        BeanUtil.copyProperties(request, sendRequest);
        FilterInvoke.loadInvokeBeforeFilters(sendRequest);
        Response response = doInvoke(request);
        Response sendResponse = new Response();
        BeanUtil.copyProperties(response, sendRequest);
        FilterInvoke.loadInvokeAfterFilters(sendResponse);
        return response;
    }

    /**
     * 子类实现公共调用
     *
     * @param request
     * @return
     */
    protected abstract Response doInvoke(Request request);
}
