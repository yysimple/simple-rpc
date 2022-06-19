package com.simple.test;

import com.simple.rpc.statistic.StatisticMain;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-06-19 12:14
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StatisticMain.class)
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
