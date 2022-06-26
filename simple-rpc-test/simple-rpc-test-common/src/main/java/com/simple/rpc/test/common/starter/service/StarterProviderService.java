package com.simple.rpc.test.common.starter.service;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-06-25 00:55
 **/
public interface StarterProviderService {

    /**
     * 测试服务数量
     *
     * @param name
     * @return
     */
    String provider(String name);

    /**
     * 用于构建树
     *
     * @param name
     * @return
     */
    String p1(String name);

    /**
     * 用于构建树；异常的情况
     *
     * @param name
     * @return
     */
    String e1(String name);
}
