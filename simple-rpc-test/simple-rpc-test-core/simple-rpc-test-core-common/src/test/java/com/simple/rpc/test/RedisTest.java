package com.simple.rpc.test;

import com.alibaba.fastjson.JSON;
import com.simple.rpc.common.config.SimpleRpcUrl;
import com.simple.rpc.core.network.message.Request;
import com.simple.rpc.core.register.strategy.RedisRegisterCenter;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.Map;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-05-06 14:33
 **/
public class RedisTest {

    RedisRegisterCenter registerCenter = new RedisRegisterCenter();

    private void initRedis() {
        SimpleRpcUrl url = new SimpleRpcUrl();
        url.setHost("127.0.0.1");
        url.setPort(6379);
        url.setPassword("123456");
        registerCenter.init(url);
    }

    @Test
    public void testHash() {
        initRedis();
        Jedis jedis = registerCenter.jedis();
        jedis.hsetnx("user", "name", "wcx");
        jedis.hsetnx("user", "name", "zyy");
        jedis.hsetnx("user", "age", "18");
        jedis.hsetnx("user", "age", "20");
    }

    @Test
    public void testHashRegisterInfo() {
        initRedis();
        Jedis jedis = registerCenter.jedis();
        String fieldKey = "com.simple.AService_aService";
        Request request = new Request();
        request.setBeanName("aService");
        request.setRequestId(100L);
//        jedis.hset(fieldKey, "127.0.0.1-41200", JSON.toJSONString(request));
//        jedis.hset(fieldKey, "127.0.0.1-41201", JSON.toJSONString(request));
        jedis.hset(fieldKey, "127.0.0.1-41202", JSON.toJSONString(request));
    }

    public static Map<String, String> getAll(Jedis jedis, String key) {
        Map<String, String> result = jedis.hgetAll(key);
        return result;
    }


    @Test
    public void parseHashRegisterInfo() {
        initRedis();
        Jedis jedis = registerCenter.jedis();
        String fieldKey = "com.simple.AService_aService";
        Request request = new Request();
        request.setBeanName("aService");
        Map<String, String> all = getAll(jedis, fieldKey);
        System.out.println(all);
    }

    @Test
    public void del() {
        initRedis();
        Jedis jedis = registerCenter.jedis();
        String key = "com.simple.rpc.test.common.starter.service.StarterHelloService_starterHelloService";
        String fieldKey = "127.0.0.1_41203";
        System.out.println(jedis.hdel(key, fieldKey));
    }

    @Test
    public void get(){
        initRedis();
        Jedis jedis = registerCenter.jedis();
        String key = "com.simple.rpc.test.common.starter.service.StarterHelloService_starterHelloService";
        String fieldKey = "127.0.0.1_41202";
        System.out.println(jedis.hget(key, fieldKey));

    }
}
