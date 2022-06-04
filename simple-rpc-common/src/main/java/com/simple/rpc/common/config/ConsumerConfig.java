package com.simple.rpc.common.config;

import lombok.Data;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-04-22 17:45
 **/
@Data
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
}
