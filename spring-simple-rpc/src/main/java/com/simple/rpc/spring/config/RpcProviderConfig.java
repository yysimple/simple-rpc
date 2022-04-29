package com.simple.rpc.spring.config;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-04-22 23:40
 **/
public class RpcProviderConfig {

    /**
     * 接口
     */
    private String interfaceName;

    /**
     * 映射
     */
    private String ref;

    /**
     * 别名
     */
    private String alias;

    /**
     * ip
     */
    private String host;

    /**
     * 端口
     */
    private int port;

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getBeanName() {
        return ref;
    }

    public void setBeanName(String ref) {
        this.ref = ref;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
