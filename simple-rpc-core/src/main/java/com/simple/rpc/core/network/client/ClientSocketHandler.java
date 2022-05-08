package com.simple.rpc.core.network.client;

import com.alibaba.fastjson.JSON;
import com.simple.rpc.core.constant.enums.CompressType;
import com.simple.rpc.core.constant.enums.MessageType;
import com.simple.rpc.core.constant.enums.SerializeType;
import com.simple.rpc.core.exception.network.NettyResponseException;
import com.simple.rpc.core.network.message.Response;
import com.simple.rpc.core.network.message.RpcMessage;
import com.simple.rpc.core.network.send.SyncWriteFuture;
import com.simple.rpc.core.network.send.SyncWriteMap;
import com.simple.rpc.core.util.SimpleRpcLog;
import io.netty.channel.*;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.apache.commons.logging.impl.SimpleLog;
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
public class ClientSocketHandler extends SimpleChannelInboundHandler<RpcMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcMessage rpcMessage) throws Exception {
        // 拿到响应值
        Response msg = (Response) rpcMessage.getData();
        long requestId = rpcMessage.getRequestId();
        // 拿到此次请求的id，对应的缓存信息
        SyncWriteFuture future = (SyncWriteFuture) SyncWriteMap.syncKey.get(requestId);
        // 这里拿到了结果，就设置响应值
        if (future != null) {
            future.setResponse(msg);
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            // 心跳
            IdleState state = ((IdleStateEvent) evt).state();
            if (state == IdleState.WRITER_IDLE) {
                SimpleRpcLog.info("write idle happen [{}]", ctx.channel().remoteAddress());
                Channel channel = ctx.channel();
                RpcMessage rpcMessage = new RpcMessage();
                rpcMessage.setSerializeType(SerializeType.PROTOSTUFF.getValue());
                rpcMessage.setCompressTye(CompressType.GZIP.getValue());
                rpcMessage.setMessageType(MessageType.HEARTBEAT.getValue());
                channel.writeAndFlush(rpcMessage).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
        SimpleRpcLog.error(cause.getMessage());
    }
}
