package com.simple.rpc.springboot;

import cn.hutool.core.util.StrUtil;
import com.simple.rpc.common.annotation.SimpleRpcReference;
import com.simple.rpc.common.annotation.SimpleRpcService;
import com.simple.rpc.common.cache.ApplicationCache;
import com.simple.rpc.common.cache.SimpleRpcServiceCache;
import com.simple.rpc.common.config.CommonConfig;
import com.simple.rpc.common.config.ConsumerConfig;
import com.simple.rpc.common.config.LocalAddressInfo;
import com.simple.rpc.common.config.SimpleRpcUrl;
import com.simple.rpc.common.constant.enums.HealthStatus;
import com.simple.rpc.common.interfaces.RegisterCenter;
import com.simple.rpc.common.util.ClassLoaderUtils;
import com.simple.rpc.common.util.SimpleRpcLog;
import com.simple.rpc.core.network.message.Request;
import com.simple.rpc.core.reflect.RpcProxy;
import com.simple.rpc.core.register.RegisterCenterFactory;
import com.simple.rpc.springboot.config.BootBaseConfig;
import com.simple.rpc.springboot.config.BootRegisterConfig;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-05-04 17:09
 **/
public class ServiceBeanPostProcessor implements BeanPostProcessor, Ordered {

    @Resource
    private BootRegisterConfig bootRegisterConfig;
    @Resource
    private BootBaseConfig bootBaseConfig;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        // 判断是否是被@SimpleRpcService注解的类
        SimpleRpcService rpcService = bean.getClass().getAnnotation(SimpleRpcService.class);
        SimpleRpcUrl simpleRpcUrl = SimpleRpcUrl.toSimpleRpcUrl(bootRegisterConfig);
        // rpc 服务需要发布到注册中心
        if (rpcService != null) {
            RegisterCenter registerCenter = RegisterCenterFactory.create(simpleRpcUrl.getType());
            Request request = new Request();
            String applicationName = ApplicationCache.APPLICATION_NAME;
            request.setApplicationName(applicationName);
            request.setLoadBalanceRule(bootBaseConfig.getLoadBalanceRule());
            request.setSerializer(bootBaseConfig.getSerializer());
            request.setCompressor(bootBaseConfig.getCompressor());
            request.setRegister(bootBaseConfig.getRegister());
            request.setWeights(bootBaseConfig.getWeights());
            request.setHost(LocalAddressInfo.LOCAL_HOST);
            request.setPort(LocalAddressInfo.PORT);
            request.setHealth(HealthStatus.IS_HEALTH.getCode());
            Class<?>[] interfaces = bean.getClass().getInterfaces();
            if (!CollectionUtils.isEmpty(Arrays.asList(interfaces))) {
                for (Class<?> anInterface : interfaces) {
                    String alias = getBeanName(anInterface.getCanonicalName());
                    request.setAlias(StrUtil.isBlank(rpcService.alias()) ? alias : rpcService.alias());
                    request.setBeanName(alias);
                    request.setInterfaceName(anInterface.getCanonicalName());
                    String registerKey = registerCenter.register(Request.request2Register(request));
                    // 将对应的bean存入到缓存之中
                    SimpleRpcServiceCache.addService(registerKey, bean);
                }
            }
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
                    ConsumerConfig consumerConfig = buildConsumerConfig(field, rpcReference);
                    proxy = RpcProxy.invoke(ClassLoaderUtils.forName(consumerConfig.getInterfaceName()), buildCommonConfig(consumerConfig));
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

    private ConsumerConfig buildConsumerConfig(Field field, SimpleRpcReference simpleRpcReference) {
        ConsumerConfig consumerConfig = new ConsumerConfig();
        String canonicalName = field.getType().getCanonicalName();
        consumerConfig.setAlias(StrUtil.isBlank(simpleRpcReference.alias()) ? getBeanName(canonicalName) : simpleRpcReference.alias());
        consumerConfig.setBeanName(getBeanName(canonicalName));
        consumerConfig.setInterfaceName(canonicalName);
        return consumerConfig;
    }

    private CommonConfig buildCommonConfig(ConsumerConfig c) {
        CommonConfig config = new CommonConfig();
        config.setConsumerConfig(c);
        config.setRegistryConfig(bootRegisterConfig);
        config.setBaseConfig(bootBaseConfig);
        return config;
    }

    private String getBeanName(String interfaceName) {
        if (!StrUtil.isBlank(interfaceName)) {
            String substring = interfaceName.substring(interfaceName.lastIndexOf(".") + 1);
            return StrUtil.lowerFirst(substring);
        }
        return interfaceName;
    }

    @Override
    public int getOrder() {
        return 20;
    }

}
