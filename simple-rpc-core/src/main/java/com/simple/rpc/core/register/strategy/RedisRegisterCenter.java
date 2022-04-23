package com.simple.rpc.core.register.strategy;

import com.alibaba.fastjson.JSON;
import com.simple.rpc.core.network.message.Request;
import com.simple.rpc.core.register.AbstractRegisterCenter;
import com.simple.rpc.core.register.config.RegisterProperties;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

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
    public void init(RegisterProperties registerProperties) {
        // 池基本配置
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(5);
        config.setTestOnBorrow(false);
        JedisPool jedisPool = new JedisPool(config, registerProperties.getHost(), registerProperties.getPort(), 10 * 1000, registerProperties.getPassword());
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
        return jedis.sadd(request.getInterfaceName() + "_" + request.getAlias(), JSON.toJSONString(request)) > 1;
    }

    /**
     * 获取服务
     *
     * @param request
     * @return
     */
    @Override
    public String get(Request request) {
        return jedis.srandmember(request.getInterfaceName() + "_" + request.getAlias());
    }

    public static Jedis jedis() {
        return jedis;
    }

}
