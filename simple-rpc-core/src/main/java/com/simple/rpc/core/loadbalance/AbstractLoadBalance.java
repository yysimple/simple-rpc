package com.simple.rpc.core.loadbalance;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.simple.rpc.common.interfaces.SimpleRpcLoadBalance;
import com.simple.rpc.common.interfaces.entity.LoadBalanceParam;
import com.simple.rpc.common.util.SimpleRpcLog;
import com.simple.rpc.core.network.message.Request;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

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
    public String loadBalance(Map<String, String> services) {
        if (CollectionUtil.isEmpty(services)) {
            return null;
        }
        Map<String, LoadBalanceParam> selectMap = new ConcurrentHashMap<>(4);
        Set<String> urls = services.keySet();
        for (String url : urls) {
            Request request = JSON.parseObject(services.get(url), Request.class);
            LoadBalanceParam param = new LoadBalanceParam();
            param.setWeights(request.getWeights());
            selectMap.put(url, param);
        }
        String selectUrl = select(selectMap);
        return services.get(selectUrl);
    }

    /**
     * 实际的负载算法
     *
     * @param urls
     * @return
     */
    public abstract String select(Map<String, LoadBalanceParam> urls);
}
