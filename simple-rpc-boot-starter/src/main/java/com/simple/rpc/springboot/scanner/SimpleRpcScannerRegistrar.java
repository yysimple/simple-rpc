package com.simple.rpc.springboot.scanner;

import cn.hutool.core.util.StrUtil;
import com.simple.rpc.common.annotation.SimpleRpcService;
import com.simple.rpc.common.util.SimpleRpcLog;
import com.simple.rpc.springboot.annotaton.SimpleRpcScan;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 扫描RPC类并注册
 *
 * @author: WuChengXing
 * @create: 2022-05-04 17:08
 **/
public class SimpleRpcScannerRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware {

    /**
     * 服务扫描的基础包，是 @RpcScan 的哪个属性
     */
    private static final String SERVER_SCANNER_BASE_PACKAGE_FIELD = "basePackages";

    /**
     * 默认基础包
     */
    private static final String[] DEFAULT_SCANNER_BASE_PACKAGES = {"com.simple.rpc"};

    private ResourceLoader resourceLoader;

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    /**
     * 注册bean信息，丢到spring容器中
     *
     * @param importingClassMetadata
     * @param registry
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        //扫描注解
        Map<String, Object> annotationAttributes = importingClassMetadata.getAnnotationAttributes(SimpleRpcScan.class.getName());
        SimpleRpcScanner serviceScanner = new SimpleRpcScanner(registry, SimpleRpcService.class);
        if (resourceLoader != null) {
            serviceScanner.setResourceLoader(resourceLoader);
        }
        String[] serviceBasePackages = (String[]) annotationAttributes.get(SERVER_SCANNER_BASE_PACKAGE_FIELD);
        if (serviceBasePackages.length < 1) {
            serviceBasePackages = DEFAULT_SCANNER_BASE_PACKAGES;
        }
        int serviceCount = serviceScanner.scan(serviceBasePackages);
        SimpleRpcLog.info(StrUtil.format("serviceScanner. packages={}, count={}", serviceBasePackages, serviceCount));
    }
}
