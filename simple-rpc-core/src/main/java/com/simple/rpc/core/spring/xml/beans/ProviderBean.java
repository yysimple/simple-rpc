package com.simple.rpc.core.spring.xml.beans;

import com.alibaba.fastjson.JSON;
import com.simple.rpc.core.config.LocalAddressInfo;
import com.simple.rpc.core.constant.enums.RegisterEnum;
import com.simple.rpc.core.exception.spring.BeanNotFoundException;
import com.simple.rpc.core.network.message.Request;
import com.simple.rpc.core.register.RegisterCenter;
import com.simple.rpc.core.register.RegisterCenterFactory;
import com.simple.rpc.core.spring.xml.config.ProviderConfig;
import com.simple.rpc.core.spring.xml.transfer.BaseData;
import com.simple.rpc.core.spring.xml.transfer.DataMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

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

    BaseData baseData = DataMap.baseDataTransfer.get(DataMap.BASE_DATA_TRANSFER);
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        RegisterCenter registerCenter = RegisterCenterFactory.create(baseData.getRegisterType());
        if (Objects.isNull(registerCenter)) {
            throw new BeanNotFoundException("注册中心未初始化");
        }
        Request providerRequest = new Request();
        providerRequest.setInterfaceName(interfaceName);
        providerRequest.setBeanName(ref);
        providerRequest.setAlias(alias);
        providerRequest.setHost(LocalAddressInfo.LOCAL_HOST);
        providerRequest.setPort(LocalAddressInfo.PORT);

        //注册生产者
        boolean flag = registerCenter.register(providerRequest);

        logger.info("注册生产者：{}, 是否成功：{} ", JSON.toJSONString(providerRequest), flag);
    }
}
