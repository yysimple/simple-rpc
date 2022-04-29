package com.simple.rpc.spring.config;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 生产者提供配置
 *
 * @author: WuChengXing
 * @create: 2022-04-22 09:32
 **/
public class ProviderConfig {

    /**
     * 接口
     */
    protected String interfaceName;

    /**
     * 映射
     */
    protected String beanName;

    /**
     * 别名
     */
    protected String alias;

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String ref) {
        this.beanName = ref;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
