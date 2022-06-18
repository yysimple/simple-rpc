package com.simple.rpc.common.interfaces.entity;

import com.simple.rpc.common.exception.SimpleRpcBaseException;
import lombok.Data;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-06-19 01:41
 **/
@Data
public class ProtocolAddress {

    private String protocol;
    private String host;
    private Integer port;

    public static ProtocolAddress parseProtocol(String url) {
        ProtocolAddress protocolAddress = new ProtocolAddress();
        String[] split = url.split("://");
        if (split.length < 1) {
            throw new SimpleRpcBaseException("不支持该注册中心地址格式");
        }
        protocolAddress.setProtocol(split[0]);
        String[] infos = split[1].split(":");
        if (infos.length < 1) {
            throw new SimpleRpcBaseException("不支持该注册中心地址格式");
        }
        protocolAddress.setHost(infos[0]);
        protocolAddress.setPort(Integer.valueOf(infos[1]));
        return protocolAddress;
    }
}
