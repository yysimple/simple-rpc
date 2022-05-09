package com.simple.rpc.core.loadbalance;

import cn.hutool.core.collection.CollectionUtil;
import com.simple.rpc.common.interfaces.SimpleRpcLoadBalance;
import com.simple.rpc.common.util.SimpleRpcLog;

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
    public String loadBalance(Map<String, String> services) {
        if (CollectionUtil.isEmpty(services)) {
            return null;
        }
        Set<String> urls = services.keySet();
        String selectUrl = select(new ArrayList<>(urls));
        SimpleRpcLog.info("负载的信息：" + selectUrl);
        return services.get(selectUrl);
    }

    protected abstract String select(List<String> urls);
}
