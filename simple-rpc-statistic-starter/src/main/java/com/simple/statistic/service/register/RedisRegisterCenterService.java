package com.simple.statistic.service.register;

import com.simple.statistic.entity.ApplicationEntity;

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
     * 获取所有的接口信息
     *
     * @return
     */
    Set<String> allService();

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
