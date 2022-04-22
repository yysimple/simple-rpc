package com.simple.rpc.core.register;

import com.simple.rpc.core.constant.enums.RegisterEnum;
import com.simple.rpc.core.register.config.RegisterProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 注册中心工厂
 *
 * @author: WuChengXing
 * @create: 2022-04-22 22:09
 **/
@Service
public class RegisterCenterFactory<T extends RegisterProperties> {

    @Autowired
    private Map<String, RegisterCenter<T>> registerCenters = new HashMap<>(4);

    public RegisterCenterContext<T> create(String registerType) {
        if (RegisterEnum.REDIS.getRegisterType().equals(registerType)) {
            return new RegisterCenterContext<>(registerCenters.get(registerType));
        }
        return null;
    }
}
