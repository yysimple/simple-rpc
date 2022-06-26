package com.simple.rpc.test.common.starter.service;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-05-26 21:25
 **/
public interface ProviderTwoService {

    String helloProviderTwo(String msg);

    String helloProviderTwoNoArgs();

    String pt1(String name);

    String et1(String name);
}
