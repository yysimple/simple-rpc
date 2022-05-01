package com.simple.rpc.spring;

import com.simple.rpc.spring.beans.ConsumerBean;
import com.simple.rpc.spring.beans.ProviderBean;
import com.simple.rpc.spring.beans.ServerBean;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: xml命名空间解析器
 *
 * @author: WuChengXing
 * @create: 2022-04-23 01:03
 **/
public class RpcNamespaceXmlHandler extends NamespaceHandlerSupport {

    /**
     * 命名空间初始化
     */
    @Override
    public void init() {
        registerBeanDefinitionParser("server", new RpcXmlBeanDefinitionParser(ServerBean.class));
        registerBeanDefinitionParser("provider", new RpcXmlBeanDefinitionParser(ProviderBean.class));
        registerBeanDefinitionParser("consumer", new RpcXmlBeanDefinitionParser(ConsumerBean.class));
    }
}
