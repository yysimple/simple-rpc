package com.simple.rpc.core.register.strategy;

import com.alibaba.fastjson.JSON;
import com.simple.rpc.core.config.entity.SimpleRpcUrl;
import com.simple.rpc.core.constant.SymbolConstant;
import com.simple.rpc.core.constant.enums.LoadBalanceRule;
import com.simple.rpc.core.loadbalance.LoadBalanceFactory;
import com.simple.rpc.core.loadbalance.SimpleRpcLoadBalance;
import com.simple.rpc.core.network.message.Request;
import com.simple.rpc.core.register.AbstractRegisterCenter;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Map;
import java.util.Objects;

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

    /**
     * 注册服务
     *
     * @param request
     * @return
     */
    @Override
    public Boolean register(Request request) {
        String key = request.getInterfaceName() + SymbolConstant.UNDERLINE + request.getAlias();
        String fieldKey = request.getHost() + SymbolConstant.UNDERLINE + request.getPort();
        String value = JSON.toJSONString(request);
        Long hset = jedis.hset(key, fieldKey, value);
        return hset > 0L;
    }

    /**
     * 获取服务
     *
     * @param request
     * @return
     */
    @Override
    public String get(Request request) {
        String key = request.getInterfaceName() + SymbolConstant.UNDERLINE + request.getAlias();
        Map<String, String> stringStringMap = jedis.hgetAll(key);
        String rule = Objects.isNull(request.getLoadBalanceRule()) ? LoadBalanceRule.ROUND.getName() : request.getLoadBalanceRule();
        return LoadBalanceFactory.create(rule).loadBalance(stringStringMap);
    }

    public static Jedis jedis() {
        return jedis;
    }

}
