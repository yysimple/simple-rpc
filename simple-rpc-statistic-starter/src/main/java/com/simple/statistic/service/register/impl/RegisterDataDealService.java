package com.simple.statistic.service.register.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.simple.rpc.common.interfaces.entity.RegisterInfo;
import com.simple.statistic.entity.ApplicationEntity;
import com.simple.statistic.service.register.RedisRegisterCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

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

    private Map<String, Map<String, Map<String, String>>> APP_SERVICE_HOST_MAP = new HashMap<>(16);

    @Override
    public Set<String> allKeys() {
        return getListKey();
    }

    @Override
    public Map<String, Map<String, Map<String, String>>> initCache() {
        getListKey().parallelStream().forEach(key -> {
            BoundHashOperations boundHashOperations = redisTemplate.boundHashOps(key);
            Set keys = boundHashOperations.keys();
            keys.forEach(hashKey -> {
                String requestInfo = boundHashOperations.get(hashKey).toString();
                JSONObject jsonObject = JSONObject.parseObject(requestInfo);
                String applicationName = jsonObject.get("applicationName").toString();
                addService(applicationName, key, (String) hashKey, requestInfo);
            });
        });
        return APP_SERVICE_HOST_MAP;
    }

    private void addService(String appName, String serviceName, String hostPort, String requestInfo) {
        // 拿到 serviceName为key的map
        Map<String, Map<String, String>> serviceMap = APP_SERVICE_HOST_MAP.get(appName);
        if (Objects.isNull(serviceMap)) {
            // 初始化serviceName为key的map
            serviceMap = new HashMap<>(16);
            // 初始化 host_port 为key的map；
            Map<String, String> map = new HashMap<>(4);
            map.put(hostPort, requestInfo);
            serviceMap.put(serviceName, map);
            APP_SERVICE_HOST_MAP.put(appName, serviceMap);
        } else {
            // 已经存在接口注册了，获取对应的map
            Map<String, String> map = serviceMap.get(serviceName);
            if (Objects.isNull(map)) {
                // 对应serviceName未注册到缓存，则初始化
                map = new HashMap<>(4);
                map.put(hostPort, requestInfo);
                // 将初始化的map放入到缓存里面
                serviceMap.put(serviceName, map);
            } else {
                // 之前已经有对应的serviceName进行了注册，只是新增集群信息；
                map.put(hostPort, requestInfo);
            }
        }
    }

    /**
     * 获取所有的key
     */
    public Set<String> getListKey() {
        Set<String> keys = redisTemplate.keys("*");
        return keys;
    }

    @Override
    public Set<String> allApplication() {
        Set<String> keys = redisTemplate.keys("*");
        return null;
    }

    @Override
    public Set<String> allService() {
        return null;
    }

    @Override
    public List<ApplicationEntity> listApp(String name) {
        // 根据应用名拿到对应的key
        Set<String> keys = redisTemplate.keys(name);
        List<ApplicationEntity> applicationEntities = new ArrayList<>();
        // 然后在拿到对应的ServiceName
        keys.forEach(key -> {
            ApplicationEntity applicationEntity = new ApplicationEntity();
            applicationEntity.setApplicationName(key);
            List<String> services = redisTemplate.opsForList().range(key, 0, -1);
            if (!CollectionUtil.isEmpty(services)) {
                applicationEntity.setServiceNum((long) services.size());
                Set<String> hosts = redisTemplate.boundHashOps(services.get(0)).keys();
                applicationEntity.setMachineNum(CollectionUtil.isEmpty(hosts) ? 0 : hosts.size());
            } else {
                applicationEntity.setServiceNum(0L);
                applicationEntity.setMachineNum(0);
            }
            applicationEntities.add(applicationEntity);
        });
        return applicationEntities;
    }
}
