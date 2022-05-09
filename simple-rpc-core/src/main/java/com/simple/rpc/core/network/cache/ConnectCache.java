package com.simple.rpc.core.network.cache;

import cn.hutool.core.collection.CollectionUtil;
import com.simple.rpc.common.constant.SymbolConstant;
import com.simple.rpc.core.network.message.Request;
import io.netty.channel.ChannelFuture;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 连接缓存
 *
 * @author: WuChengXing
 * @create: 2022-05-06 23:28
 **/
public class ConnectCache {

    /**
     * 数据结构：{"com.simple.rpc.AService_aService": {"127.0.0.1_41200" : "channel001"}}
     */
    public static Map<String, Map<String, ChannelFuture>> CHANNEL_MAP = new ConcurrentHashMap<>(16);

    public static boolean saveChannelFuture(Request request) {
        if (Objects.isNull(request)) {
            return false;
        }
        String key = request.getInterfaceName() + SymbolConstant.UNDERLINE + request.getAlias();
        String url = request.getHost() + SymbolConstant.UNDERLINE + request.getPort();
        Map<String, ChannelFuture> hostPortMap = CHANNEL_MAP.get(key);
        // 判断是否是第一次，针对于接口做了缓存
        if (CollectionUtil.isEmpty(hostPortMap)) {
            hostPortMap = new ConcurrentHashMap<>(4);
        }
        // 如果url存在就覆盖
        hostPortMap.put(url, request.getChannelFuture());
        CHANNEL_MAP.put(key, hostPortMap);
        return true;
    }

    public static ChannelFuture getChannelFuture(Request request) {
        if (Objects.isNull(request)) {
            return null;
        }
        String key = request.getInterfaceName() + SymbolConstant.UNDERLINE + request.getAlias();
        String url = request.getHost() + SymbolConstant.UNDERLINE + request.getPort();
        Map<String, ChannelFuture> stringChannelMap = CHANNEL_MAP.get(key);
        if (!CollectionUtil.isEmpty(stringChannelMap)) {
            return stringChannelMap.get(url);
        }
        return null;
    }
}
