package com.simple.statistic;

import lombok.Data;
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
    private RedisTemplate redisTemplate;

    @Test
    public void testSaveHash() {
        redisTemplate.boundHashOps("user").put("name", "zyy");
        redisTemplate.boundHashOps("user").put("age", "18");
    }

    @Test
    public void testHash() {
        redisTemplate.boundHashOps("");
    }

    @Data
    class User{
        private String name;
        private Integer age;
    }
}