package com.simple.rpc.core.loadbalance.rule.random;

import cn.hutool.core.util.RandomUtil;
import com.simple.rpc.common.interfaces.entity.LoadBalanceParam;
import com.simple.rpc.core.loadbalance.AbstractLoadBalance;

import java.util.ArrayList;
import java.util.Map;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 随机
 *
 * @author: WuChengXing
 * @create: 2022-05-06 17:00
 **/
public class RandomRule extends AbstractLoadBalance {

    @Override
    public String select(Map<String, LoadBalanceParam> urls) {
        ArrayList<String> strings = new ArrayList<>(urls.keySet());
        int size = strings.size();
        int index = RandomUtil.randomInt(size);
        return strings.get(index);
    }
}
