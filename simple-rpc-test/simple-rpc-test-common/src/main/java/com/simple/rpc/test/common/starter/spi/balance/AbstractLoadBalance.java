package com.simple.rpc.test.common.starter.spi.balance;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.simple.rpc.common.interfaces.SimpleRpcLoadBalance;
import com.simple.rpc.common.interfaces.entity.LoadBalanceParam;
import com.simple.rpc.common.util.SimpleRpcLog;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 抽象负载
 *
 * @author: WuChengXing
 * @create: 2022-05-12 09:53
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
            JSONObject request = JSON.parseObject(services.get(url));
            LoadBalanceParam param = new LoadBalanceParam();
            param.setWeights((Integer) request.get("weights"));
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
