package com.simple.statistic.entity.response;

import com.simple.rpc.common.interfaces.entity.RegisterInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: rpc服务相关的实体
 *
 * @author: WuChengXing
 * @create: 2022-06-25 13:14
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class ServiceEntity extends RegisterInfo {

    /**
     * 服务存储是的key值
     */
    private String serviceKey;

    /**
     * 机器数量
     */
    private Integer machineNum;

    /**
     * url信息
     */
    private String url;

}
