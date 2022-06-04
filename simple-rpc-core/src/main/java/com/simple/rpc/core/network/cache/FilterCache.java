package com.simple.rpc.core.network.cache;

import com.simple.rpc.common.constant.FilterConstant;
import com.simple.rpc.common.interfaces.SimpleRpcFilter;
import com.simple.rpc.core.filter.InvokeAfterFilter;
import com.simple.rpc.core.filter.InvokeBeforeFilter;
import com.simple.rpc.core.filter.RemoteInvokeBeforeFilter;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 过滤器缓存
 *
 * @author: WuChengXing
 * @create: 2022-06-05 02:07
 **/
public class FilterCache {

    public static final String INVOKE_BEFORE_FILTER = FilterConstant.INVOKE_BEFORE_FILTER;
    public static final String INVOKE_AFTER_FILTER = FilterConstant.INVOKE_AFTER_FILTER;
    public static final String REMOTE_INVOKE_BEFORE_FILTER = FilterConstant.REMOTE_INVOKE_BEFORE_FILTER;

    public static Map<String, List<InvokeBeforeFilter>> INVOKE_BEFORE_FILTERS = new ConcurrentHashMap<>(4);
    public static Map<String, List<InvokeAfterFilter>> INVOKE_AFTER_FILTERS = new ConcurrentHashMap<>(4);
    public static Map<String, List<RemoteInvokeBeforeFilter>> REMOTE_INVOKE_BEFORE_FILTERS = new ConcurrentHashMap<>(4);

    public static List<InvokeBeforeFilter> allInvokeBeforeFilters() {
        return INVOKE_BEFORE_FILTERS.get(INVOKE_BEFORE_FILTER);
    }

    public static List<InvokeAfterFilter> allInvokeAfterFilter() {
        return INVOKE_AFTER_FILTERS.get(INVOKE_AFTER_FILTER);
    }

    public static List<RemoteInvokeBeforeFilter> allRemoteInvokeBeforeFilter() {
        return REMOTE_INVOKE_BEFORE_FILTERS.get(REMOTE_INVOKE_BEFORE_FILTER);
    }

    @SuppressWarnings("all")
    public static <T extends SimpleRpcFilter> T addInvokeBeforeFilter(String filterType, T filter) {
        if (INVOKE_BEFORE_FILTER.equals(filterType)) {
            return (T) addInvokeBeforeFilter((InvokeBeforeFilter) filter);
        } else if (INVOKE_AFTER_FILTER.equals(filterType)) {
            return (T) addInvokeAfterFilter((InvokeAfterFilter) filter);
        } else if (REMOTE_INVOKE_BEFORE_FILTER.equals(filterType)) {
            return (T) addRemoteInvokeBeforeFilter((RemoteInvokeBeforeFilter) filter);
        }
        return null;
    }

    public static InvokeBeforeFilter addInvokeBeforeFilter(InvokeBeforeFilter invokeBeforeFilter) {
        allInvokeBeforeFilters().add(invokeBeforeFilter);
        return invokeBeforeFilter;
    }

    public static InvokeAfterFilter addInvokeAfterFilter(InvokeAfterFilter invokeAfterFilter) {
        allInvokeAfterFilter().add(invokeAfterFilter);
        return invokeAfterFilter;
    }

    public static RemoteInvokeBeforeFilter addRemoteInvokeBeforeFilter(RemoteInvokeBeforeFilter remoteInvokeBeforeFilter) {
        allRemoteInvokeBeforeFilter().add(remoteInvokeBeforeFilter);
        return remoteInvokeBeforeFilter;
    }
}
