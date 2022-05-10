package com.simple.rpc.core.register.strategy;

import com.simple.rpc.common.constant.SymbolConstant;
import com.simple.rpc.core.config.entity.SimpleRpcUrl;
import com.simple.rpc.core.network.server.hook.HookEntity;
import com.simple.rpc.core.register.AbstractRegisterCenter;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

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
        JedisPool jedisPool = new JedisPool(config, url.getHost(), url.getPort(), 10 * 1000, url.getPassword());
        jedis = jedisPool.getResource();
    }

    @Override
    protected Boolean buildDataAndSave(String key, String hostPort, String request) {
        return jedis.hset(key, hostPort, request) > 0;
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
        return hdel.get() > 0;
    }

    public static Jedis jedis() {
        return jedis;
    }

}
