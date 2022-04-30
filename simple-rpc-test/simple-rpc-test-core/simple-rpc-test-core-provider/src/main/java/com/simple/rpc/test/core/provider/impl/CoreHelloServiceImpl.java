package com.simple.rpc.test.core.provider.impl;

import com.simple.rpc.test.common.core.entity.UserInfo;
import com.simple.rpc.test.common.core.service.CoreHelloService;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-04-30 13:41
 **/
public class CoreHelloServiceImpl implements CoreHelloService {

    @Override
    public String hello(UserInfo userInfo) {
        return "hello: " + userInfo.getUsername();
    }
}
