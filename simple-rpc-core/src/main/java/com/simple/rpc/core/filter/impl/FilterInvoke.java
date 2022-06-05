package com.simple.rpc.core.filter.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.simple.rpc.core.filter.InvokeAfterFilter;
import com.simple.rpc.core.filter.InvokeBeforeFilter;
import com.simple.rpc.core.filter.RemoteInvokeBeforeFilter;
import com.simple.rpc.core.network.cache.FilterCache;
import com.simple.rpc.core.network.message.Request;
import com.simple.rpc.core.network.message.Response;

import java.util.List;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 过滤器调用
 *
 * @author: WuChengXing
 * @create: 2022-06-05 13:56
 **/
public class FilterInvoke {

    public static void loadInvokeBeforeFilters(Request request) {
        List<InvokeBeforeFilter> invokeBeforeFilters = FilterCache.allInvokeBeforeFilters();
        if (!CollectionUtil.isEmpty(invokeBeforeFilters)) {
            for (InvokeBeforeFilter invokeBeforeFilter : invokeBeforeFilters) {
                invokeBeforeFilter.invokeBefore(request);
            }
        }
    }

    public static void loadInvokeAfterFilters(Response response) {
        List<InvokeAfterFilter> invokeAfterFilters = FilterCache.allInvokeAfterFilter();
        if (!CollectionUtil.isEmpty(invokeAfterFilters)) {
            for (InvokeAfterFilter invokeAfterFilter : invokeAfterFilters) {
                invokeAfterFilter.invokeAfter(response);
            }
        }
    }

    public static void loadRemoteInvokeAfterFilters(Request request) {
        List<RemoteInvokeBeforeFilter> remoteInvokeBeforeFilters = FilterCache.allRemoteInvokeBeforeFilter();
        if (!CollectionUtil.isEmpty(remoteInvokeBeforeFilters)) {
            for (RemoteInvokeBeforeFilter remoteInvokeBeforeFilter : remoteInvokeBeforeFilters) {
                remoteInvokeBeforeFilter.invokeBefore(request);
            }
        }
    }
}
