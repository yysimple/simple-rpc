package com.simple.rpc.test.common.starter.spi.balance;

import cn.hutool.core.util.RandomUtil;
import com.simple.rpc.common.interfaces.entity.LoadBalanceParam;
import com.simple.rpc.common.util.SimpleRpcLog;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 轮询权重算法
 *
 * @author: WuChengXing
 * @create: 2022-05-12 09:47
 **/
public class RandomWeightRule extends AbstractLoadBalance {

    /**
     * 权重:
     * - 127.0.0.1_8881: 20
     * - 127.0.0.1_8882: 30
     * - 127.0.0.1_8883: 50
     * <p>
     * 拉长范围：|0 -- 127.0.0.1_8881 -- 20 | 21 —- 127.0.0.1_8882 -- 50 | 51 -- 127.0.0.1_8883 -- 100|
     *
     * @param urls
     * @return
     */
    @Override
    public String select(Map<String, LoadBalanceParam> urls) {
        SimpleRpcLog.warn("== 随机权重负载算法 ==");
        int range = 0;
        for (LoadBalanceParam value : urls.values()) {
            range += value.getWeights();
        }
        int index = RandomUtil.randomInt(range);
        for (String url : urls.keySet()) {
            LoadBalanceParam param = urls.get(url);
            Integer weights = param.getWeights();
            if (index < weights) {
                return url;
            }
            index -= weights;
        }
        return "";
    }

    public static void main(String[] args) {
        Map<String, LoadBalanceParam> urls = new ConcurrentHashMap<>(4);
        LoadBalanceParam param1 = new LoadBalanceParam();
        param1.setWeights(20);
        LoadBalanceParam param2 = new LoadBalanceParam();
        param2.setWeights(30);
        LoadBalanceParam param3 = new LoadBalanceParam();
        param3.setWeights(50);
        urls.put("127.0.0.1_8081", param1);
        urls.put("127.0.0.1_8082", param2);
        urls.put("127.0.0.1_8083", param3);
        RandomWeightRule rule = new RandomWeightRule();
        System.out.println(rule.select(urls));
    }
}
