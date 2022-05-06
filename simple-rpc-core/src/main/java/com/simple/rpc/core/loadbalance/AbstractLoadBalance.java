package com.simple.rpc.core.loadbalance;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.simple.rpc.core.network.message.Request;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-05-06 16:48
 **/
public abstract class AbstractLoadBalance implements SimpleRpcLoadBalance {

    @Override
    public Request loadBalance(Map<String, String> services) {
        if (CollectionUtil.isEmpty(services)) {
            return new Request();
        }
        Set<String> urls = services.keySet();
        String requestInfo = services.get(select(new ArrayList<>(urls)));
        return JSON.parseObject(requestInfo, Request.class);
    }

    protected abstract String select(List<String> urls);
}
