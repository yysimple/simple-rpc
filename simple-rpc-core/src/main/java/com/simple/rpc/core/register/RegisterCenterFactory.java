package com.simple.rpc.core.register;

import com.simple.rpc.core.constant.enums.RegisterEnum;
import com.simple.rpc.core.register.strategy.RedisRegisterCenter;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 注册中心工厂
 *
 * @author: WuChengXing
 * @create: 2022-04-22 22:09
 **/
public class RegisterCenterFactory {

    public static RegisterCenter create(String registerType) {
        if (RegisterEnum.REDIS.getRegisterType().equals(registerType)) {
            return new RedisRegisterCenter();
        }
        return null;
    }
}
