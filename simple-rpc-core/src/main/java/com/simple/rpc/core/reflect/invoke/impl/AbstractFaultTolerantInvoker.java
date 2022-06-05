package com.simple.rpc.core.reflect.invoke.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.simple.rpc.core.filter.InvokeAfterFilter;
import com.simple.rpc.core.filter.InvokeBeforeFilter;
import com.simple.rpc.core.filter.SpiLoadFilter;
import com.simple.rpc.core.network.cache.FilterCache;
import com.simple.rpc.core.network.message.Request;
import com.simple.rpc.core.network.message.Response;
import com.simple.rpc.core.reflect.invoke.FaultTolerantInvoker;

import java.util.List;

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
        loadInvokeBeforeFilters(sendRequest);
        Response response = doInvoke(request);
        Response sendResponse = new Response();
        BeanUtil.copyProperties(response, sendRequest);
        loadInvokeAfterFilters(sendResponse);
        return response;
    }

    /**
     * 子类实现公共调用
     *
     * @param request
     * @return
     */
    protected abstract Response doInvoke(Request request);

    private void loadInvokeBeforeFilters(Request request) {
        List<InvokeBeforeFilter> invokeBeforeFilters = FilterCache.allInvokeBeforeFilters();
        if (!CollectionUtil.isEmpty(invokeBeforeFilters)) {
            for (InvokeBeforeFilter invokeBeforeFilter : invokeBeforeFilters) {
                invokeBeforeFilter.invokeBefore(request);
            }
        }
    }

    private void loadInvokeAfterFilters(Response response) {
        List<InvokeAfterFilter> invokeAfterFilters = FilterCache.allInvokeAfterFilter();
        if (!CollectionUtil.isEmpty(invokeAfterFilters)) {
            for (InvokeAfterFilter invokeAfterFilter : invokeAfterFilters) {
                invokeAfterFilter.invokeAfter(response);
            }
        }
    }
}
