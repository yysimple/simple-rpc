package com.simple.rpc.springboot;

import cn.hutool.core.util.StrUtil;
import com.simple.rpc.core.annotation.SimpleRpcReference;
import com.simple.rpc.core.annotation.SimpleRpcService;
import com.simple.rpc.core.config.entity.ConsumerConfig;
import com.simple.rpc.core.config.entity.LocalAddressInfo;
import com.simple.rpc.core.config.entity.RegistryConfig;
import com.simple.rpc.core.config.entity.SimpleRpcUrl;
import com.simple.rpc.core.network.cache.RegisterInfoCache;
import com.simple.rpc.core.network.cache.SimpleRpcServiceCache;
import com.simple.rpc.core.network.message.Request;
import com.simple.rpc.core.reflect.RpcProxy;
import com.simple.rpc.core.register.RegisterCenter;
import com.simple.rpc.core.register.RegisterCenterFactory;
import com.simple.rpc.core.util.ClassLoaderUtils;
import com.simple.rpc.core.util.SimpleRpcLog;
import com.simple.rpc.springboot.config.BootRegisterConfig;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Field;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-05-04 17:09
 **/
@Component
@ConditionalOnClass({ServerInit.class})
public class ServiceBeanPostProcessor implements BeanPostProcessor {

    @Resource
    private BootRegisterConfig bootRegisterConfig;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        // 判断是否是被@SimpleRpcService注解的类
        SimpleRpcService rpcService = bean.getClass().getAnnotation(SimpleRpcService.class);
        SimpleRpcUrl simpleRpcUrl = SimpleRpcUrl.toSimpleRpcUrl(bootRegisterConfig);
        // rpc 服务需要发布到注册中心
        if (rpcService != null) {
            RegisterCenter registerCenter = RegisterCenterFactory.create(simpleRpcUrl.getType());
            Request request = buildRequest(bean, rpcService, beanName);
            registerCenter.register(request);
            // 将对应的bean存入到缓存之中
            SimpleRpcServiceCache.addService(request.getAlias(), bean);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field : fields) {
            SimpleRpcReference rpcReference = field.getAnnotation(SimpleRpcReference.class);
            if (rpcReference != null) {
                // 生成代理对象
                Object proxy = null;
                try {
                    ConsumerConfig consumerConfig = buildConsumerConfig(field, rpcReference, beanName);
                    proxy = RpcProxy.invoke(ClassLoaderUtils.forName(consumerConfig.getInterfaceName()), bootRegisterConfig, consumerConfig);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                field.setAccessible(true);
                try {
                    // 设置字段
                    field.set(bean, proxy);
                } catch (IllegalAccessException e) {
                    SimpleRpcLog.error("field.set error. bean={}, field={}", bean.getClass(), field.getName(), e);
                }
            }
        }
        return bean;
    }

    private Request buildRequest(Object bean, SimpleRpcService rpcService, String beanName) {
        Request request = new Request();
        // 这里如果别名为空，那么就用beanName
        request.setAlias(StrUtil.isBlank(rpcService.alias()) ? beanName : rpcService.alias());
        request.setInterfaceName(bean.getClass().getCanonicalName());
        request.setBeanName(beanName);
        request.setHost(LocalAddressInfo.LOCAL_HOST);
        request.setPort(LocalAddressInfo.PORT);
        return request;
    }

    private ConsumerConfig buildConsumerConfig(Field field, SimpleRpcReference simpleRpcReference, String beanName) {
        ConsumerConfig consumerConfig = new ConsumerConfig();
        consumerConfig.setAlias(StrUtil.isBlank(simpleRpcReference.alias()) ? beanName : simpleRpcReference.alias());
        consumerConfig.setBeanName(beanName);
        consumerConfig.setInterfaceName(field.getType().getCanonicalName());
        return consumerConfig;
    }
}
