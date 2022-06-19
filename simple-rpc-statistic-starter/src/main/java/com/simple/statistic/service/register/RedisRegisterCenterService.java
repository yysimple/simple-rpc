package com.simple.statistic.service.register;

import java.util.Set;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-06-19 22:43
 **/
public interface RedisRegisterCenterService {

    /**
     * 获取所有的key
     */
    Set<String> allKeys();
}
