package com.simple.statistic.service.register;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-06-19 01:45
 **/
@Service
public class RegisterDataDealService implements RedisRegisterCenterService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Set<String> allKeys() {
        return getListKey();
    }

    /**
     * 获取所有的key
     */
    public Set<String> getListKey() {
        Set<String> keys = redisTemplate.keys("*");
        return keys;
    }
}
