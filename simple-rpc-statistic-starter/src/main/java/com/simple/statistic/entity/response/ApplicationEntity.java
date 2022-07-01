package com.simple.statistic.entity.response;

import lombok.Data;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-06-20 00:19
 **/
@Data
public class ApplicationEntity {

    /**
     * 应用名
     */
    private String applicationName;

    /**
     * 机器数量
     */
    private Integer machineNum;

    /**
     * 服务接口数量
     */
    private Long serviceNum;
}
