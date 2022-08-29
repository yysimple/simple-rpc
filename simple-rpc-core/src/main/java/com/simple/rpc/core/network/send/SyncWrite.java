package com.simple.rpc.core.network.send;

import com.simple.rpc.common.constant.MessageFormatConstant;
import com.simple.rpc.common.constant.enums.CompressType;
import com.simple.rpc.common.constant.enums.MessageType;
import com.simple.rpc.common.constant.enums.SerializeType;
import com.simple.rpc.common.exception.network.NettyInitException;
import com.simple.rpc.common.exception.network.NettyResponseException;
import com.simple.rpc.common.util.SimpleRpcLog;
import com.simple.rpc.core.network.message.Request;
import com.simple.rpc.core.network.message.Response;
import com.simple.rpc.core.network.message.RpcMessage;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 同步写工具类
 *
 * @author: WuChengXing
 * @create: 2022-04-19 15:09
 **/
public class SyncWrite {

    /**
     * 同步写数据
     *
     * @param channel
     * @param request
     * @param timeout
     * @return
     * @throws Exception
     */
    public Response writeAndSync(Channel channel, Request request, Long timeout) throws Exception {
        if (channel == null) {
            throw new NettyInitException("channel is null, please init channel");
        }
        if (request == null) {
            throw new NullPointerException("request");
        }
        if (timeout <= 0) {
            timeout = 30L;
        }
        long requestId = MessageFormatConstant.REQUEST_ID.incrementAndGet();
        request.setRequestId(requestId);
        // 记录此次请求id，并放入到缓存中
        WriteFuture<Response> future = new SyncWriteFuture(request.getRequestId());
        SyncWriteMap.CLIENT_REQUEST.put(request.getRequestId(), future);
        // 构建请求数据
        RpcMessage rpcMessage = new RpcMessage();
        rpcMessage.setMessageType(MessageType.REQUEST.getValue());
        rpcMessage.setRequestId(requestId);
        byte serializer = SerializeType.fromName(request.getSerializer()).getValue();
        rpcMessage.setSerializeType(serializer);
        byte compressor = CompressType.fromName(request.getCompressor()).getValue();
        rpcMessage.setCompressTye(compressor);
        rpcMessage.setData(request);
        // 同步写数据
        Response response = doWriteAndSync(channel, rpcMessage, timeout, future);
        // 拿到响应值后，此前请求结束，那么可以移除此次请求
        SyncWriteMap.CLIENT_REQUEST.remove(request.getRequestId());
        return response;
    }

    private Response doWriteAndSync(final Channel channel, final RpcMessage rpcMessage, final long timeout, final WriteFuture<Response> writeFuture) throws Exception {
        // 这里就不用lambda了，这里就是在channel写出一条数据之后，可以为其添加一个监听时间，也即操作完之后的一个回调方法
        // 每个 Netty 的出站 I/O 操作都将返回一个 ChannelFuture
        SimpleRpcLog.warn("客户端同步写操作！！");
        channel.writeAndFlush(rpcMessage).addListener((ChannelFutureListener) future -> {
            // 设置此次请求的状态
            writeFuture.setWriteResult(future.isSuccess());
            // 如果失败，此次的原因
            writeFuture.setCause(future.cause());
            // 失败移除此次请求
            if (!writeFuture.isWriteSuccess()) {
                SyncWriteMap.CLIENT_REQUEST.remove(writeFuture.requestId());
            }
        });

        // 请求完成之后，这里会去模拟等待，get的时候是无法去拿到资源的，这里设置一个等待事件
        Response response = writeFuture.get(timeout, TimeUnit.SECONDS);
        if (response == null) {
            // 没有响应直接移除当次请求
            SyncWriteMap.CLIENT_REQUEST.remove(writeFuture.requestId());
            // 已经超时则抛出异常
            if (writeFuture.isTimeout()) {
                throw new TimeoutException();
            } else {
                // write exception
                throw new NettyResponseException(writeFuture.cause());
            }
        }
        // 否则返回响应，此次类似 feign的调用，等到请求，过了一段时间还没有拿到结果，则抛出超时异常，否则成功
        return response;
    }
}
