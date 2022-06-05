package com.simple.rpc.core.filter.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.simple.rpc.common.constant.FilterConstant;
import com.simple.rpc.common.interfaces.SimpleRpcFilter;
import com.simple.rpc.common.spi.ExtensionLoader;
import com.simple.rpc.core.filter.InvokeAfterFilter;
import com.simple.rpc.core.filter.InvokeBeforeFilter;
import com.simple.rpc.core.filter.RemoteInvokeBeforeFilter;
import com.simple.rpc.core.network.cache.FilterCache;

import java.util.Map;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: spi机制加载filter
 *
 * @author: WuChengXing
 * @create: 2022-06-05 11:47
 **/
public class SpiLoadFilter {

    public static void loadFilters() {
        ExtensionLoader<SimpleRpcFilter> loader = ExtensionLoader.getLoader(SimpleRpcFilter.class);
        Map<String, Class<?>> extensionClassesCache = loader.getExtensionClassesCache();
        if (!CollectionUtil.isEmpty(extensionClassesCache)) {
            extensionClassesCache.forEach((k, v) -> {
                try {
                    if (InvokeBeforeFilter.class.isAssignableFrom(v)) {
                        FilterCache.addInvokeFilter(FilterConstant.INVOKE_BEFORE_FILTER, v.newInstance());
                    } else if (InvokeAfterFilter.class.isAssignableFrom(v)) {
                        FilterCache.addInvokeFilter(FilterConstant.INVOKE_AFTER_FILTER, v.newInstance());
                    } else if (RemoteInvokeBeforeFilter.class.isAssignableFrom(v)) {
                        FilterCache.addInvokeFilter(FilterConstant.REMOTE_INVOKE_BEFORE_FILTER, v.newInstance());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
