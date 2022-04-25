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
     * beanName
     */
    protected String beanName;

    /**
     * 别名
     */
    protected String alias;

    /**
     * 消费者重试次数，未连接成功则抛出异常
     */
    protected Integer tryNum;

    /**
     * 超时时间
     */
    protected Long timeout;

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

    public Integer getTryNum() {
        return tryNum;
    }

    public void setTryNum(Integer tryNum) {
        this.tryNum = tryNum;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public Long getTimeout() {
        return timeout;
    }

    public void setTimeout(Long timeout) {
        this.timeout = timeout;
    }
}
