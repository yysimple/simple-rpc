package com.simple.rpc.spring.beans;

import com.alibaba.fastjson.JSON;
import com.simple.rpc.core.config.entity.LocalAddressInfo;
import com.simple.rpc.core.config.entity.ProviderConfig;
import com.simple.rpc.core.config.entity.SimpleRpcUrl;
import com.simple.rpc.core.network.cache.SimpleRpcServiceCache;
import com.simple.rpc.core.network.message.Request;
import com.simple.rpc.core.register.RegisterCenter;
import com.simple.rpc.core.register.RegisterCenterFactory;
import com.simple.rpc.core.util.SimpleRpcLog;
import com.simple.rpc.spring.beans.parser.ParseServerBean;
import com.simple.rpc.spring.exception.BeanNotFoundException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-04-22 22:02
 **/
public class ProviderBean extends ProviderConfig implements ApplicationContextAware {

    @Resource
    private ServerBean serverBean;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SimpleRpcUrl simpleRpcUrl = ParseServerBean.parse(serverBean);
        RegisterCenter registerCenter = RegisterCenterFactory.create(simpleRpcUrl.getType());
        if (Objects.isNull(registerCenter)) {
            throw new BeanNotFoundException("注册中心未初始化");
        }
        Request providerRequest = new Request();
        providerRequest.setInterfaceName(interfaceName);
        providerRequest.setBeanName(beanName);
        providerRequest.setAlias(alias);
        providerRequest.setHost(LocalAddressInfo.LOCAL_HOST);
        providerRequest.setPort(LocalAddressInfo.PORT);
        //注册生产者
        boolean flag = registerCenter.register(providerRequest);
        SimpleRpcServiceCache.addService(alias, applicationContext.getBean(beanName));
        SimpleRpcLog.info("注册生产者：{}, 是否成功：{} ", JSON.toJSONString(providerRequest), flag);
    }
}
