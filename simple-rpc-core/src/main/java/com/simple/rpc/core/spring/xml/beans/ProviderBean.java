package com.simple.rpc.core.spring.xml.beans;

import com.alibaba.fastjson.JSON;
import com.simple.rpc.core.config.LocalAddressInfo;
import com.simple.rpc.core.exception.spring.BeanNotFoundException;
import com.simple.rpc.core.network.message.Request;
import com.simple.rpc.core.register.RegisterCenter;
import com.simple.rpc.core.register.RegisterCenterFactory;
import com.simple.rpc.core.register.config.RegisterProperties;
import com.simple.rpc.core.spring.xml.config.ProviderConfig;
import com.simple.rpc.core.spring.xml.transfer.DataMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
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
    private Logger logger = LoggerFactory.getLogger(ProviderBean.class);

    RegisterProperties dataTransfer = DataMap.dataTransfer.get(DataMap.DATA_TRANSFER);

    @Resource
    private RegisterProperties registerProperties;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        if (Objects.isNull(registerProperties) || Objects.isNull(registerProperties.getRegisterType())) {
            registerProperties = new RegisterProperties();
            BeanUtils.copyProperties(dataTransfer, registerProperties);
        }
        RegisterCenter registerCenter = RegisterCenterFactory.create(registerProperties.getRegisterType());
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

        logger.info("注册生产者：{}, 是否成功：{} ", JSON.toJSONString(providerRequest), flag);
    }
}
