package com.simple.rpc.springboot.scanner;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.lang.annotation.Annotation;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 扫描类
 *
 * @author: WuChengXing
 * @create: 2022-05-04 17:07
 **/
public class SimpleRpcScanner extends ClassPathBeanDefinitionScanner {

    @SafeVarargs
    public SimpleRpcScanner(BeanDefinitionRegistry registry, Class<? extends Annotation>... annotationTypes) {
        // 注册bean信息
        super(registry);
        // 获取包含对应注解的类
        for (Class<? extends Annotation> annotationType : annotationTypes) {
            super.addIncludeFilter(new AnnotationTypeFilter(annotationType));
        }
    }
}
