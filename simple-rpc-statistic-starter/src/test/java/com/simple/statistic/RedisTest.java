package com.simple.statistic;

import com.simple.statistic.service.register.RedisRegisterCenterService;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.Map;

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

    @Resource
    private RedisRegisterCenterService redisRegisterCenterService;

    @Test
    public void testSaveHash() {
        redisTemplate.boundHashOps("user").put("name", "zyy");
        redisTemplate.boundHashOps("user").put("age", "18");
    }

    @Test
    public void testHash() {
        redisTemplate.boundHashOps("");
    }

    @Test
    public void initCache(){
        Map<String, Map<String, Map<String, String>>> stringMapMap = redisRegisterCenterService.initCache();
        System.out.println(stringMapMap);
        System.out.println("== init finish ==");
    }
}
