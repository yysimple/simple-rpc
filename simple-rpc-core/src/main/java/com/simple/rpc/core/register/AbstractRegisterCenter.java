package com.simple.rpc.core.register;

import com.alibaba.fastjson.JSON;
import com.simple.rpc.common.constant.SymbolConstant;
import com.simple.rpc.common.constant.enums.LoadBalanceRule;
import com.simple.rpc.common.interfaces.RegisterCenter;
import com.simple.rpc.common.interfaces.SimpleRpcLoadBalance;
import com.simple.rpc.common.config.SimpleRpcUrl;
import com.simple.rpc.common.interfaces.entity.RegisterInfo;
import com.simple.rpc.common.network.HookEntity;
import com.simple.rpc.common.spi.ExtensionLoader;

import java.util.Map;
import java.util.Objects;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 注册中心公共抽象类
 *
 * @author: WuChengXing
 * @create: 2022-04-21 18:44
 **/
public abstract class AbstractRegisterCenter implements RegisterCenter {

    @Override
    public void init(SimpleRpcUrl url) {

    }

    @Override
    public Boolean register(RegisterInfo request) {
        String key = request.getInterfaceName() + SymbolConstant.UNDERLINE +
                request.getAlias();
        String fieldKey = request.getHost() + SymbolConstant.UNDERLINE + request.getPort();
        String value = JSON.toJSONString(request);
        return buildDataAndSave(key, fieldKey, value);
    }

    /**
     * 将三个字段构建然后存入：
     *                                          --- 127.0.0.1_41201 --- {"alias":"xxx","host":"127.0.0.1","port":41201,"serializer":"serializer","weights":20}
     *                                          -
     * com.simple.rpc.HelloService_helloService --- 127.0.0.1_41202 --- {"alias":"xxx","host":"127.0.0.1","port":41202,"serializer":"serializer","weights":30}
     *                                          -
     *                                          --- 127.0.0.1_41203 --- {"alias":"xxx","host":"127.0.0.1","port":41203,"serializer":"serializer","weights":50}
     *
     * @param key
     * @param hostPort
     * @param request
     * @return
     */
    protected abstract Boolean buildDataAndSave(String key, String hostPort, String request);

    @Override
    public String get(RegisterInfo request) {
        String key = request.getInterfaceName() + SymbolConstant.UNDERLINE +
                request.getAlias();
        Map<String, String> stringStringMap = getLoadBalanceData(key);
        String rule = Objects.isNull(request.getLoadBalanceRule()) ? LoadBalanceRule.ROUND.getName() : request.getLoadBalanceRule();
        return ExtensionLoader.getLoader(SimpleRpcLoadBalance.class).getExtension(rule).loadBalance(stringStringMap);
    }

    /**
     * 数据格式：{"127.0.0.1_41200" : "{"requestId: 1"}"}
     * 描述：通过 key（com.simple.rpc.AService_aService）获取的到 下面的map格式
     * map格式：前面的key以 host + "_" + port 组成；后面是对应的request信息的json格式
     *
     * @param key
     * @return
     */
    protected abstract Map<String, String> getLoadBalanceData(String key);

    @Override
    public Boolean unregister(HookEntity hookEntity) {
        return null;
    }
}
