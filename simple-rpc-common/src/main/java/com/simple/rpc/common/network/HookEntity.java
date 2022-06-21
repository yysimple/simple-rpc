package com.simple.rpc.common.network;

import lombok.Data;

import java.util.List;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 退出钩子函数实体
 *
 * @author: WuChengXing
 * @create: 2022-05-10 15:09
 **/
@Data
public class HookEntity {

    /**
     * 注册的rpc服务的名称：这里的话是 com.simple.rpc.AService_aService
     */
    private List<String> rpcServiceNames;

    /**
     * 对应停止服务的server端的连接信息
     */
    private String serverUrl;

    private Integer serverPort;

    /**
     * 注册中心类型
     */
    private String registerType;

    private String applicationName;
}
