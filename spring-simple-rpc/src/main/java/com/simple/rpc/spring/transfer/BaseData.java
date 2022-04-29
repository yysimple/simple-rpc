package com.simple.rpc.spring.transfer;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-04-24 09:43
 **/
public class BaseData {
    /**
     * 重试次数
     */
    private Integer tryNum;

    /**
     * 超时时间
     */
    private Long timeout;

    /**
     * 注册中心类型
     */
    private String registerType;

    public Integer getTryNum() {
        return tryNum;
    }

    public void setTryNum(Integer tryNum) {
        this.tryNum = tryNum;
    }

    public Long getTimeout() {
        return timeout;
    }

    public void setTimeout(Long timeout) {
        this.timeout = timeout;
    }

    public String getRegisterType() {
        return registerType;
    }

    public void setRegisterType(String registerType) {
        this.registerType = registerType;
    }
}
