package com.simple.rpc.spring.beans;

import com.simple.rpc.common.util.ClassLoaderUtils;
import com.simple.rpc.common.config.BaseConfig;
import com.simple.rpc.common.config.CommonConfig;
import com.simple.rpc.common.config.ConsumerConfig;
import com.simple.rpc.common.config.RegistryConfig;
import com.simple.rpc.common.exception.network.NettyInitException;
import com.simple.rpc.core.reflect.RpcProxy;
import com.simple.rpc.spring.beans.parser.ParseServerBean;
import com.simple.rpc.spring.exception.BeanNotFoundException;
import com.simple.rpc.spring.transfer.BaseData;
import org.springframework.beans.factory.FactoryBean;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 服务消费者
 *
 * @author: WuChengXing
 * @create: 2022-04-22 22:01
 **/
public class ConsumerBean<T> extends ConsumerConfig implements FactoryBean<T> {

    @Resource
    private ServerBean serverBean;

    @Override
    public T getObject() throws BeanNotFoundException, NettyInitException, ClassNotFoundException {
        RegistryConfig registryConfig = ParseServerBean.serverToRegister(serverBean);
        BaseConfig baseConfig = new BaseConfig();
        baseConfig.setLoadBalanceRule("round");
        CommonConfig config = new CommonConfig();
        config.setConsumerConfig(this);
        config.setRegistryConfig(registryConfig);
        config.setBaseConfig(baseConfig);
        return (T) RpcProxy.invoke(ClassLoaderUtils.forName(interfaceName), config);
    }

    @Override
    public Class<?> getObjectType() {
        try {
            if (!Objects.isNull(interfaceName)) {
                return ClassLoaderUtils.forName(interfaceName);
            }
            return null;
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    private Integer calcTryNum(BaseData baseData, Integer tryNum) {
        if (!Objects.isNull(tryNum) && tryNum >= 0) {
            return tryNum;
        }
        if (!Objects.isNull(baseData) && !Objects.isNull(baseData.getTryNum()) && baseData.getTryNum() >= 0) {
            return baseData.getTryNum();
        }
        return 100;
    }

    private Long calcTimeout(BaseData baseData, Long timeout) {
        if (!Objects.isNull(timeout) && timeout >= 0) {
            return timeout;
        }
        if (!Objects.isNull(baseData) && !Objects.isNull(baseData.getTimeout()) && baseData.getTimeout() >= 0) {
            return baseData.getTimeout();
        }
        return 30L;
    }
}
