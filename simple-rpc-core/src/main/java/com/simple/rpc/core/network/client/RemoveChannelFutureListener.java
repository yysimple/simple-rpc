package com.simple.rpc.core.network.client;

import com.simple.rpc.common.constant.SymbolConstant;
import com.simple.rpc.common.util.SimpleRpcLog;
import com.simple.rpc.core.network.cache.ConnectCache;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.util.concurrent.GenericFutureListener;

import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.List;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 心跳链接不上会断开连接
 *
 * @author: WuChengXing
 * @create: 2022-08-28 03:02
 **/
public interface RemoveChannelFutureListener extends GenericFutureListener<ChannelFuture> {

    ChannelFutureListener BEAT_FAILURE_REMOVE_CHANNEL = future -> {
        if (!future.isSuccess()) {
            InetSocketAddress inetSocketAddress = (InetSocketAddress) future.channel().remoteAddress();
            String hostString = inetSocketAddress.getHostString();
            int port = inetSocketAddress.getPort();
            String url = hostString + SymbolConstant.UNDERLINE + port;
            SimpleRpcLog.warn("future removing: ===> {}", url);
            List<String> urls = Arrays.asList(url);
            ConnectCache.remove(urls);
        }

    };
}
