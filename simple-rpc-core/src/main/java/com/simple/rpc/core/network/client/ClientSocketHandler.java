package com.simple.rpc.core.network.client;

import com.simple.rpc.common.constant.enums.CompressType;
import com.simple.rpc.common.constant.enums.MessageType;
import com.simple.rpc.common.constant.enums.SerializeType;
import com.simple.rpc.common.util.SimpleRpcLog;
import com.simple.rpc.core.network.message.Response;
import com.simple.rpc.core.network.message.RpcMessage;
import com.simple.rpc.core.network.send.SyncWriteFuture;
import com.simple.rpc.core.network.send.SyncWriteMap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

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
        SyncWriteFuture future = (SyncWriteFuture) SyncWriteMap.CLIENT_REQUEST.get(requestId);
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
                channel.writeAndFlush(rpcMessage)
                        .addListeners(RemoveChannelFutureListener.BEAT_FAILURE_REMOVE_CHANNEL,
                                ChannelFutureListener.CLOSE_ON_FAILURE);
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
