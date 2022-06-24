package com.simple.rpc.test.starter.provider.impl;

import com.simple.rpc.common.annotation.SimpleRpcService;
import com.simple.rpc.test.common.starter.service.StarterProviderService;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-06-25 00:57
 **/
@SimpleRpcService
public class StarterProviderImpl implements StarterProviderService {
    @Override
    public String provider(String name) {
        return "provider: " + name;
    }
}
