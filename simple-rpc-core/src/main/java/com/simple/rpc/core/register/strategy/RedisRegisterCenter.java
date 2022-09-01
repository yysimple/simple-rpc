package com.simple.rpc.core.register.strategy;

import com.alibaba.fastjson.JSON;
import com.simple.rpc.common.constant.CommonConstant;
import com.simple.rpc.common.constant.SymbolConstant;
import com.simple.rpc.common.config.SimpleRpcUrl;
import com.simple.rpc.common.network.HookEntity;
import com.simple.rpc.core.register.AbstractRegisterCenter;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: redis注册中心
 *
 * @author: WuChengXing
 * @create: 2022-04-21 17:08
 **/
public class RedisRegisterCenter extends AbstractRegisterCenter {

    /**
     * 非切片额客户端连接
     */
    private static Jedis jedis;

    @Override
    public void init(SimpleRpcUrl url) {
        // 池基本配置
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(5);
        config.setTestOnBorrow(false);
        JedisPool jedisPool = new JedisPool(config, url.getHost(), url.getPort(), 30 * 1000, url.getPassword());
        jedis = jedisPool.getResource();
    }

    @Override
    protected Boolean buildDataAndSave(String key, String hostPort, String request) {
        return jedis.hset(key, hostPort, request) > 0;
    }

    @Override
    protected Boolean buildAppName(String appName, String rpcService) {
        return jedis.lpush(appName, rpcService) > 0;
    }

    @Override
    protected Map<String, String> getLoadBalanceData(String key) {
        return jedis.hgetAll(key);
    }

    @Override
    public Boolean unregister(HookEntity hookEntity) {
        List<String> rpcServiceNames = hookEntity.getRpcServiceNames();
        String fieldKey = hookEntity.getServerUrl() + SymbolConstant.UNDERLINE + hookEntity.getServerPort();
        AtomicReference<Long> hdel = new AtomicReference<>(0L);
        rpcServiceNames.forEach(name -> {
            hdel.set(jedis.hdel(name, fieldKey));
        });
        // 删除该应用对应的app对应的信息
        jedis.del(CommonConstant.RPC_APP_PREFIX + SymbolConstant.UNDERLINE + hookEntity.getApplicationName());
        return hdel.get() > 0;
    }

    public static Jedis jedis() {
        return jedis;
    }

    public <T> void setList(String key, List<T> list) {
        try {
            jedis.set(key.getBytes(), JSON.toJSONBytes(list));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected List<String> getMultiKeyValue(List<String> keys, String machine) {
        List<String> values = new ArrayList<>();
        keys.forEach(key -> {
            values.add(jedis.hget(key, machine));
        });
        return values;
    }
}
