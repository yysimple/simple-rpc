package com.simple.rpc.test.starter.provider.impl;

import com.simple.rpc.common.annotation.SimpleRpcService;
import com.simple.rpc.test.common.starter.service.StartAndShutdownService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 优雅上下线实现类
 *
 * @author: WuChengXing
 * @create: 2022-08-28 07:40
 **/
@SimpleRpcService
public class StartAndShutdownServiceImpl implements StartAndShutdownService {

    private final static Logger logger = LoggerFactory.getLogger(StartAndShutdownServiceImpl.class);

    @Override
    public String delayRequest() {
        logger.warn("优雅上下线接口底层调用");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "优雅上下线";
    }
}
