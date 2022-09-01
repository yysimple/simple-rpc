package com.simple.rpc.test.core.common.test.spi;

import com.simple.rpc.common.config.SimpleRpcUrl;
import com.simple.rpc.common.interfaces.RegisterCenter;
import com.simple.rpc.common.interfaces.entity.RegisterInfo;
import com.simple.rpc.common.network.HookEntity;

import java.util.Map;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-05-14 12:43
 **/
public class MysqlRegisterCenterTest implements RegisterCenter {
    @Override
    public void init(SimpleRpcUrl url) {

    }

    @Override
    public String register(RegisterInfo request) {
        return null;
    }

    @Override
    public String get(RegisterInfo request) {
        return null;
    }

    @Override
    public Boolean unregister(HookEntity hookEntity) {
        return null;
    }

    @Override
    public Boolean offline() {
        return null;
    }

    @Override
    public Boolean online() {
        return null;
    }

    @Override
    public Boolean checkHealth() {
        return null;
    }

    @Override
    public void filterNotHealth(Map<String, String> registerInfos) {

    }
}
