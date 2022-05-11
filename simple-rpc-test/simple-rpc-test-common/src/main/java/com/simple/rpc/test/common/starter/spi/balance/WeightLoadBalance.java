package com.simple.rpc.test.common.starter.spi.balance;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.simple.rpc.common.interfaces.SimpleRpcLoadBalance;
import com.simple.rpc.common.interfaces.entity.LoadBalanceParam;
import com.simple.rpc.common.util.SimpleRpcLog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 权重负载均衡
 *
 * @author: WuChengXing
 * @create: 2022-05-11 19:30
 **/
public class WeightLoadBalance implements SimpleRpcLoadBalance {

    private final static LongAdder CUR_INDEX = new LongAdder();

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
        SimpleRpcLog.warn("权重方式");
        ArrayList<String> strings = new ArrayList<>(urls);
        int index = (int) (CUR_INDEX.longValue() % strings.size());
        CUR_INDEX.increment();
        return services.get(strings.get(index));
    }

}
