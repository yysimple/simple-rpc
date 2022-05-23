package com.simple.rpc.core.network.cache;

import cn.hutool.core.collection.CollectionUtil;
import com.simple.rpc.common.constant.SymbolConstant;
import com.simple.rpc.core.network.message.Request;
import io.netty.channel.ChannelFuture;

import java.util.List;
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
     * 数据结构：{"127.0.0.1_41200" : "channel001"}
     */
    public static Map<String, ChannelFuture> CHANNEL_MAP = new ConcurrentHashMap<>(16);

    public static boolean saveChannelFuture(Request request) {
        if (Objects.isNull(request)) {
            return false;
        }
        String url = request.getHost() + SymbolConstant.UNDERLINE + request.getPort();
        ChannelFuture channelFutureCache = CHANNEL_MAP.get(url);
        // 判断是否是第一次，针对于接口做了缓存
        ChannelFuture channelFuture = request.getChannelFuture();
        if (Objects.isNull(channelFutureCache) && !Objects.isNull(channelFuture) && channelFuture.channel().isOpen()) {
            CHANNEL_MAP.put(url, channelFuture);
            return true;
        }
        return false;
    }

    public static ChannelFuture getChannelFuture(Request request) {
        if (Objects.isNull(request)) {
            return null;
        }
        String url = request.getHost() + SymbolConstant.UNDERLINE + request.getPort();
        return CHANNEL_MAP.get(url);
    }

    public static Boolean remove(List<String> urls) {
        if (CollectionUtil.isEmpty(urls)) {
            return false;
        }
        for (String url : urls) {
            ChannelFuture channelFutureCache = CHANNEL_MAP.get(url);
            channelFutureCache.channel().close();
            CHANNEL_MAP.remove(url);
        }
        return true;
    }
}
