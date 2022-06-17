package com.simple.rpc.springboot;

import com.simple.rpc.common.cache.ApplicationCache;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-06-18 00:13
 **/
@Component
public class ApplicationConfig implements ApplicationContextAware, Ordered {

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationCache.APPLICATION_NAME = applicationContext.getApplicationName();
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
