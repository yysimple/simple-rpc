package com.simple.rpc.test.starter.consumer.impl;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-06-26 12:38
 **/
public interface ConsumerInnerService {

    /**
     * 有参数无异常的内部调用
     *
     * @param name
     * @return
     */
    String hasArgNoE(String name);

    /**
     * 有参数有异常的内部调用
     *
     * @param name
     * @return
     */
    String hasArgHasE(String name);

    /**
     * 自己内部调用方法
     *
     * @return
     */
    String simpleInvoke();

    /**
     * 优雅上下线测试接口
     *
     * @return
     */
    String upAndShutdown();
}
