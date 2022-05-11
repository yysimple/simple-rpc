package com.simple.rpc.core.loadbalance.rule;

import com.simple.rpc.core.loadbalance.AbstractLoadBalance;

import java.util.List;
import java.util.concurrent.atomic.LongAdder;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 轮询
 *
 * @author: WuChengXing
 * @create: 2022-05-06 17:00
 **/
public class RoundRule extends AbstractLoadBalance {

    private final static LongAdder CUR_INDEX = new LongAdder();

    @Override
    public String select(List<String> urls) {
        int index = (int) (CUR_INDEX.longValue() % urls.size());
        CUR_INDEX.increment();
        return urls.get(index);
    }
}
