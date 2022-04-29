package com.simple.rpc.test.provider;

import com.simple.rpc.test.provider.domain.Hi;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-04-29 11:14
 **/
public interface HelloService {

    String hi();

    String say(String str);

    String sayHi(Hi hi);
}
