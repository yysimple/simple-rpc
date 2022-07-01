package com.simple.statistic.service.register;

import com.simple.statistic.entity.response.ApplicationEntity;
import com.simple.statistic.entity.response.ServiceEntity;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-06-19 22:43
 **/
public interface RedisRegisterCenterService {

    /**
     * 获取所有的key
     *
     * @return
     */
    Set<String> allKeys();

    /**
     * 获取所有的应用
     *
     * @return
     */
    Set<String> allApplication();

    /**
     * 查询所有的RPC服务
     *
     * @param serviceName
     * @return
     */
    List<ServiceEntity> listService(String serviceName);

    /**
     * 查询所有应用
     *
     * @param appName
     * @return
     */
    List<ApplicationEntity> listApp(String appName);

    /**
     * 初始化缓存
     *
     * @return
     */
    Map<String, Map<String, Map<String, String>>> initCache();
}
