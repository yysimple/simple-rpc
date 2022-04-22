package com.simple.rpc.core.spring.xml.config;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-04-22 17:45
 **/
public class ConsumerConfig {

    /**
     * 接口
     */
    protected String interfaceName;

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

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
