package com.simple.rpc.common.interfaces;

import java.util.Map;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 负载均衡接口
 *
 * @author: WuChengXing
 * @create: 2022-05-06 11:16
 **/
public interface SimpleRpcLoadBalance {

    /**
     * 负载均衡
     *
     * @param services
     * @return
     */
    String loadBalance(Map<String, String> services);

}
