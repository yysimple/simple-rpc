package com.simple.rpc.core.network.client;

import com.alibaba.fastjson.JSON;
import com.simple.rpc.core.network.message.Response;
import com.simple.rpc.core.network.send.SyncWriteFuture;
import com.simple.rpc.core.network.send.SyncWriteMap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: client端的处理器
 *
 * @author: WuChengXing
 * @create: 2022-04-19 14:46
 **/
public class ClientSocketHandler extends ChannelInboundHandlerAdapter {

    private final Logger logger = LoggerFactory.getLogger(ClientSocketHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object obj) throws Exception {
        // 拿到响应值
        Response msg = (Response) obj;
        String requestId = msg.getRequestId();
        // 拿到此次请求的id，对应的缓存信息
        SyncWriteFuture future = (SyncWriteFuture) SyncWriteMap.syncKey.get(requestId);
        logger.info("客户端拿到了响应值：{}", JSON.toJSONString(msg));
        // 这里拿到了结果，就设置响应值
        if (future != null) {
            future.setResponse(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
