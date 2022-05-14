package com.simple.rpc.core.register;

import com.simple.rpc.common.interfaces.RegisterCenter;
import com.simple.rpc.common.spi.ExtensionLoader;

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
        return getRegisterCenter(registerType);
    }

    private static RegisterCenter getRegisterCenter(String registerType) {
        return ExtensionLoader.getLoader(RegisterCenter.class).getExtension(registerType);
    }
}
