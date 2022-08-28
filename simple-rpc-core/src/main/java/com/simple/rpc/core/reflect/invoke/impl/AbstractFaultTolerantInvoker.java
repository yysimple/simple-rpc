package com.simple.rpc.core.reflect.invoke.impl;

import cn.hutool.core.bean.BeanUtil;
import com.simple.rpc.common.interfaces.entity.SimpleRpcContext;
import com.simple.rpc.core.filter.impl.FilterInvoke;
import com.simple.rpc.core.filter.impl.SpiLoadFilter;
import com.simple.rpc.core.network.message.Request;
import com.simple.rpc.core.network.message.Response;
import com.simple.rpc.core.reflect.invoke.FaultTolerantInvoker;

import java.util.Date;

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
        SimpleRpcContext context = new SimpleRpcContext();
        context.setEntryTime(new Date());
        request.setSimpleRpcContext(FilterInvoke.loadInvokeBeforeFilters(context));
        Response response = doInvoke(request);
        return FilterInvoke.loadInvokeAfterFilters(response);
    }

    /**
     * 子类实现公共调用
     *
     * @param request
     * @return
     */
    protected abstract Response doInvoke(Request request);
}
