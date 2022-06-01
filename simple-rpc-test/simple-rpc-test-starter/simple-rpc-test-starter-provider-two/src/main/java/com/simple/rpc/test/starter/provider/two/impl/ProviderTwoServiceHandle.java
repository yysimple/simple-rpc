package com.simple.rpc.test.starter.provider.two.impl;

import org.springframework.stereotype.Service;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 用于跨方法的调用
 *
 * @author: WuChengXing
 * @create: 2022-06-01 14:18
 **/
@Service
public class ProviderTwoServiceHandle {

    public String handle() {
        return "ProviderTwoServiceHandle.handle!!";
    }
}
