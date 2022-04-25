package com.simple.rpc.consumer.api.impl;

import com.simple.rpc.consumer.api.HiService;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-04-25 14:45
 **/
public class HiServiceImpl implements HiService {
    @Override
    public String hiTim(String name) {
        return "hahaha";
    }
}
