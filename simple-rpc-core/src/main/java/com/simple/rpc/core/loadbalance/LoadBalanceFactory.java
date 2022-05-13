package com.simple.rpc.core.loadbalance;

import com.simple.rpc.common.constant.enums.LoadBalanceRule;
import com.simple.rpc.common.interfaces.SimpleRpcLoadBalance;
import com.simple.rpc.core.loadbalance.rule.random.RandomRule;
import com.simple.rpc.core.loadbalance.rule.round.RoundRule;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 负载工厂
 *
 * @author: WuChengXing
 * @create: 2022-05-06 17:25
 **/
public class LoadBalanceFactory {

    public static SimpleRpcLoadBalance create(String rule) {
        if (LoadBalanceRule.RANDOM.getName().equals(rule)) {
            return new RandomRule();
        } else if (LoadBalanceRule.ROUND.getName().equals(rule)) {
            return new RoundRule();
        }
        return null;
    }
}
