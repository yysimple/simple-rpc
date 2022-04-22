package com.simple.rpc.api;

import com.simple.rpc.api.domain.Hi;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-04-22 09:42
 **/
public interface HelloService {

    String hi();

    String say(String str);

    String sayHi(Hi hi);
}
