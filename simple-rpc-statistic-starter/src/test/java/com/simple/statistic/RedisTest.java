package com.simple.statistic;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-06-19 13:06
 **/
@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    public void testSaveHash() {
        redisTemplate.boundHashOps("user").put("name", "zyy");
        redisTemplate.boundHashOps("user").put("name", "wcx");
    }

    @Test
    public void testHash() {
        redisTemplate.boundHashOps("");
    }
}
