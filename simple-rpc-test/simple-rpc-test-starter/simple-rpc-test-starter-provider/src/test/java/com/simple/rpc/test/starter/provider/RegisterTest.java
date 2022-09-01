package com.simple.rpc.test.starter.provider;

import com.simple.rpc.common.cache.RegisterInfoCache;
import com.simple.rpc.common.config.LocalAddressInfo;
import com.simple.rpc.common.config.SimpleRpcUrl;
import com.simple.rpc.common.constant.SymbolConstant;
import com.simple.rpc.common.interfaces.RegisterCenter;
import com.simple.rpc.core.register.RegisterCenterFactory;
import com.simple.rpc.springboot.config.BootRegisterConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-09-01 15:12
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class RegisterTest {

    /**
     * 非切片额客户端连接
     */
    private static Jedis jedis;

    private SimpleRpcUrl simpleRpcUrl;

    @Before
    public void parse() {
        BootRegisterConfig bootRegisterConfig = new BootRegisterConfig();
        bootRegisterConfig.setAddress("redis://127.0.0.1:6379");
        bootRegisterConfig.setPassword("123456");
        this.simpleRpcUrl = SimpleRpcUrl.toSimpleRpcUrl(bootRegisterConfig);
    }

    public void init(SimpleRpcUrl url) {
        // 池基本配置
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(5);
        config.setTestOnBorrow(false);
        JedisPool jedisPool = new JedisPool(config, url.getHost(), url.getPort(), 30 * 1000, url.getPassword());
        jedis = jedisPool.getResource();
    }

    @Test
    public void testOffline() {
        String url = LocalAddressInfo.LOCAL_HOST + SymbolConstant.UNDERLINE + LocalAddressInfo.PORT;
        RegisterCenter registerCenter = RegisterCenterFactory.create(SimpleRpcUrl.toSimpleRpcUrl(RegisterInfoCache.getRegisterInfo(url)).getType());
        // 服务暂停对外
        registerCenter.offline();
    }

    @Test
    public void testHSet() {
        init(simpleRpcUrl);
        Long hset = jedis.hset("rpcService_com.simple.rpc.test.common.starter.service.StartAndShutdownService_startAndShutdownService", "127.0.0.1_41200", "1");
        //System.out.println(jedis.hset("aa", "bb", "3"));
    }
}
