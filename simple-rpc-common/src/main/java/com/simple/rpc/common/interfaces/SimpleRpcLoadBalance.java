package com.simple.rpc.common.interfaces;

import com.simple.rpc.common.annotation.SimpleRpcSPI;
import com.simple.rpc.common.interfaces.entity.LoadBalanceParam;

import java.util.List;
import java.util.Map;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 负载均衡接口
 *
 * @author: WuChengXing
 * @create: 2022-05-06 11:16
 **/
@SimpleRpcSPI("round")
public interface SimpleRpcLoadBalance {

    /**
     * 负载均衡
     *
     * @param services
     * @return
     */
    String loadBalance(Map<String, String> services);

}
