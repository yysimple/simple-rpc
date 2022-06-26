package com.simple.rpc.test.starter.provider.impl;

import com.simple.rpc.common.annotation.SimpleRpcReference;
import com.simple.rpc.common.annotation.SimpleRpcService;
import com.simple.rpc.test.common.starter.service.ProviderTwoService;
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

    @SimpleRpcReference
    ProviderTwoService providerTwoService;

    @Override
    public String provider(String name) {
        return "provider: " + name;
    }

    @Override
    public String p1(String name) {
        String s = providerTwoService.pt1(name);
        return "p-p1: " + name + ", 远程two：" + s;
    }

    @Override
    public String e1(String name) {
        String s = providerTwoService.et1(name);
        int i = 1 / 0;
        return "出现异常：" + s;
    }
}
