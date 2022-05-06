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

    private final LongAdder curIndex = new LongAdder();

    @Override
    protected String select(List<String> urls) {
        int index = (int) (curIndex.longValue() % urls.size());
        curIndex.increment();
        return urls.get(index);
    }
}
