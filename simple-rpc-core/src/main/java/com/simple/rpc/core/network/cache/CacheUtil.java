package com.simple.rpc.core.network.cache;

import com.simple.rpc.common.constant.SymbolConstant;
import com.simple.rpc.common.util.SimpleRpcLog;
import io.netty.channel.ChannelFuture;

import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: channel缓存工具
 *
 * @author: WuChengXing
 * @create: 2022-08-28 04:11
 **/
public class CacheUtil {

    public static void deleteConnect(ChannelFuture channelFuture) {
        if (Objects.isNull(channelFuture) || Objects.isNull(channelFuture.channel())) {
            return;
        }
        InetSocketAddress inetSocketAddress = (InetSocketAddress) channelFuture.channel().remoteAddress();
        String hostString = inetSocketAddress.getHostString();
        int port = inetSocketAddress.getPort();
        String url = hostString + SymbolConstant.UNDERLINE + port;
        SimpleRpcLog.warn("future removing: ===> {}", url);
        List<String> urls = Arrays.asList(url);
        ConnectCache.remove(urls);
    }
}
