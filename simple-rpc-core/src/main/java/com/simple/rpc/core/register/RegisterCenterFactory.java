package com.simple.rpc.core.register;

import com.simple.rpc.core.constant.enums.RegisterEnum;
import com.simple.rpc.core.register.strategy.LocalRegisterCenter;
import com.simple.rpc.core.register.strategy.MysqlRegisterCenter;
import com.simple.rpc.core.register.strategy.RedisRegisterCenter;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 注册中心工厂
 *
 * @author: WuChengXing
 * @create: 2022-04-22 22:09
 **/
public class RegisterCenterFactory {

    static Map<String, RegisterCenter> REGISTER_CENTER_CACHE = new ConcurrentHashMap<>(16);

    public static RegisterCenter create(String registerType) {
        return getRegisterCenter(registerType);
    }

    private static RegisterCenter getRegisterCenter(String registerType) {
        if (Objects.isNull(REGISTER_CENTER_CACHE.get(registerType))) {
            if (RegisterEnum.LOCAL.getRegisterType().equals(registerType)) {
                REGISTER_CENTER_CACHE.put(registerType, new LocalRegisterCenter());
            } else if (RegisterEnum.REDIS.getRegisterType().equals(registerType)) {
                REGISTER_CENTER_CACHE.put(registerType, new RedisRegisterCenter());
            } else if (RegisterEnum.MYSQL.getRegisterType().equals(registerType)) {
                REGISTER_CENTER_CACHE.put(registerType, new MysqlRegisterCenter());
            }
        }
        return REGISTER_CENTER_CACHE.get(registerType);
    }
}
