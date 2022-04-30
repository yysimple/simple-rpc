package com.simple.rpc.core.network.send;

import com.simple.rpc.core.constant.MessageFormatConstant;
import com.simple.rpc.core.exception.network.NettyInitException;
import com.simple.rpc.core.exception.network.NettyResponseException;
import com.simple.rpc.core.network.message.Request;
import com.simple.rpc.core.network.message.Response;
import com.simple.rpc.core.util.DateUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;
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

    private final Logger logger = LoggerFactory.getLogger(SyncWrite.class);

    /**
     * 同步写数据
     *
     * @param channel
     * @param request
     * @param timeout
     * @return
     * @throws Exception
     */
    public Response writeAndSync(final Channel channel, final Request request, long timeout) throws Exception {

        if (channel == null) {
            throw new NettyInitException("channel is null, please init channel");
        }
        if (request == null) {
            throw new NullPointerException("request");
        }
        if (timeout <= 0) {
            timeout = 30L;
        }
        long requestId = MessageFormatConstant.REQUEST_ID.get();
        request.setRequestId(requestId);
        // 记录此次请求id，并放入到缓存中
        WriteFuture<Response> future = new SyncWriteFuture(request.getRequestId());
        SyncWriteMap.syncKey.put(request.getRequestId(), future);
        // 同步写数据
        Response response = doWriteAndSync(channel, request, timeout, future);
        // 拿到响应值后，此前请求结束，那么可以移除此次请求
        SyncWriteMap.syncKey.remove(request.getRequestId());
        return response;
    }

    private Response doWriteAndSync(final Channel channel, final Request request, final long timeout, final WriteFuture<Response> writeFuture) throws Exception {
        // 这里就不用lambda了，这里就是在channel写出一条数据之后，可以为其添加一个监听时间，也即操作完之后的一个回调方法
        // 每个 Netty 的出站 I/O 操作都将返回一个 ChannelFuture
        channel.writeAndFlush(request).addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) {
                // 设置此次请求的状态
                writeFuture.setWriteResult(future.isSuccess());
                // 如果失败，此次的原因
                writeFuture.setCause(future.cause());
                // 失败移除此次请求
                if (!writeFuture.isWriteSuccess()) {
                    SyncWriteMap.syncKey.remove(writeFuture.requestId());
                }
                logger.info("此次写请求的结果：{}, {}", writeFuture.isWriteSuccess(), writeFuture.cause());
            }
        });

        logger.info("等待服务端给我反馈，当前时间：{}", DateUtils.getTime());
        // 请求完成之后，这里会去模拟等待，get的时候是无法去拿到资源的，这里设置一个等待事件
        Response response = writeFuture.get(timeout, TimeUnit.SECONDS);
        if (response == null) {
            // 已经超时则抛出异常
            if (writeFuture.isTimeout()) {
                logger.info("此次请求已经超时，抛出超时异常，当前时间：{}", DateUtils.getTime());
                throw new TimeoutException();
            } else {
                // write exception
                throw new NettyResponseException(writeFuture.cause());
            }
        }
        logger.info("写出去后，拿到返回值，当前时间：{}", DateUtils.getTime());
        // 否则返回响应，此次类似 feign的调用，等到请求，过了一段时间还没有拿到结果，则抛出超时异常，否则成功
        return response;
    }
}
