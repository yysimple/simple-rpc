package com.simple.rpc.core.register;

import com.alibaba.fastjson.JSON;
import com.simple.rpc.common.cache.SimpleRpcServiceCache;
import com.simple.rpc.common.config.LocalAddressInfo;
import com.simple.rpc.common.constant.CommonConstant;
import com.simple.rpc.common.constant.SymbolConstant;
import com.simple.rpc.common.constant.enums.LoadBalanceRule;
import com.simple.rpc.common.interfaces.RegisterCenter;
import com.simple.rpc.common.interfaces.SimpleRpcLoadBalance;
import com.simple.rpc.common.config.SimpleRpcUrl;
import com.simple.rpc.common.interfaces.entity.RegisterInfo;
import com.simple.rpc.common.network.HookEntity;
import com.simple.rpc.common.spi.ExtensionLoader;
import com.simple.rpc.common.util.SimpleRpcLog;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

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
    public String register(RegisterInfo request) {
        String key = CommonConstant.RPC_SERVICE_PREFIX + SymbolConstant.UNDERLINE + request.getInterfaceName() + SymbolConstant.UNDERLINE +
                request.getAlias();
        String fieldKey = request.getHost() + SymbolConstant.UNDERLINE + request.getPort();
        String value = JSON.toJSONString(request);
        String appName = CommonConstant.RPC_APP_PREFIX + SymbolConstant.UNDERLINE + request.getApplicationName();
        buildAppName(appName, key);
        buildDataAndSave(key, fieldKey, value);
        return key;
    }

    /**
     * 将三个字段构建然后存入：
     * --- 127.0.0.1_41201 --- {"alias":"xxx","host":"127.0.0.1","port":41201,"serializer":"serializer","weights":20}
     * -
     * com.simple.rpc.HelloService_helloService --- 127.0.0.1_41202 --- {"alias":"xxx","host":"127.0.0.1","port":41202,"serializer":"serializer","weights":30}
     * -
     * --- 127.0.0.1_41203 --- {"alias":"xxx","host":"127.0.0.1","port":41203,"serializer":"serializer","weights":50}
     *
     * @param key
     * @param hostPort
     * @param request
     * @return
     */
    protected abstract Boolean buildDataAndSave(String key, String hostPort, String request);

    /**
     * 构建appName - rpcService相关的数据
     *
     * @param appName
     * @param rpcService
     * @return
     */
    protected abstract Boolean buildAppName(String appName, String rpcService);

    @Override
    public String get(RegisterInfo request) {
        String key = CommonConstant.RPC_SERVICE_PREFIX + SymbolConstant.UNDERLINE + request.getInterfaceName() + SymbolConstant.UNDERLINE +
                request.getAlias();
        Map<String, String> stringStringMap = getLoadBalanceData(key);
        // 过滤已下线的注册信息
        filterNotHealth(stringStringMap);
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

    @Override
    public Boolean offline() {
        long start = System.currentTimeMillis();
        String machine = LocalAddressInfo.LOCAL_HOST + SymbolConstant.UNDERLINE + LocalAddressInfo.PORT;
        List<String> serviceNames = SimpleRpcServiceCache.allKey();
        List<String> multiKeyValue = this.getMultiKeyValue(serviceNames, machine);
        Boolean updateHealth = updateHealth(multiKeyValue, "0");
        SimpleRpcLog.warn("预下线操作，状态：【{}】, 耗时：【{}】", updateHealth, System.currentTimeMillis() - start);
        return updateHealth;
    }

    private Boolean updateHealth(List<String> multiKeyValues, String health) {
        AtomicReference<Integer> updateNum = new AtomicReference<>(0);
        multiKeyValues.forEach(s -> {
            RegisterInfo registerInfo = JSON.parseObject(s, RegisterInfo.class);
            registerInfo.setHealth(health);
            updateNum.getAndSet(updateNum.get() + 1);
            this.register(registerInfo);
        });
        return multiKeyValues.size() == updateNum.get();
    }

    /**
     * hmget命令去获取对应的值
     *
     * @param keys
     * @param machine
     * @return
     */
    protected abstract List<String> getMultiKeyValue(List<String> keys, String machine);

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
        registerInfos.forEach((k, v) -> {
            String s = registerInfos.get(k);
            RegisterInfo registerInfo = JSON.parseObject(s, RegisterInfo.class);
            // 过滤掉已经下线的服务
            if ("0".equals(registerInfo.getHealth())) {
                registerInfos.remove(k);
            }
        });
    }
}
