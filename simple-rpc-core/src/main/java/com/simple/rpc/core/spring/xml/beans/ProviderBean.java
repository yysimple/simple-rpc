package com.simple.rpc.core.spring.xml.beans;

import com.alibaba.fastjson.JSON;
import com.simple.rpc.core.config.LocalAddressInfo;
import com.simple.rpc.core.network.message.Request;
import com.simple.rpc.core.register.strategy.RedisRegisterCenter;
import com.simple.rpc.core.spring.xml.config.ProviderConfig;
import com.simple.rpc.core.spring.xml.config.RpcProviderConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.Resource;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-04-22 22:02
 **/
public class ProviderBean extends ProviderConfig implements ApplicationContextAware {
    private Logger logger = LoggerFactory.getLogger(ProviderBean.class);

    @Resource
    private RedisRegisterCenter redisRegisterCenter;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        Request providerRequest = new Request();
        providerRequest.setInterfaceName(interfaceName);
        providerRequest.setRef(ref);
        providerRequest.setAlias(alias);
        providerRequest.setHost(LocalAddressInfo.LOCAL_HOST);
        providerRequest.setPort(LocalAddressInfo.PORT);

        //注册生产者
        boolean flag = redisRegisterCenter.register(providerRequest);

        logger.info("注册生产者：{}, 是否成功：{} ", JSON.toJSONString(providerRequest), flag);
    }
}
