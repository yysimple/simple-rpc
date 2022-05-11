package com.simple.rpc.core.loadbalance.rule;

import cn.hutool.core.util.RandomUtil;
import com.simple.rpc.core.loadbalance.AbstractLoadBalance;

import java.util.List;

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
    public String select(List<String> urls) {
        int size = urls.size();
        int index = RandomUtil.randomInt(size);
        return urls.get(index);
    }
}
