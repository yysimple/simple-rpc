package com.simple.rpc.core.network.client;

import com.simple.rpc.core.network.codec.RpcMessageDecoder;
import com.simple.rpc.core.network.codec.RpcMessageEncoder;
import com.simple.rpc.core.network.message.Request;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: RPC客户端代码
 *
 * @author: WuChengXing
 * @create: 2022-04-18 19:45
 **/
public class RpcClientSocket implements Runnable {

    private ChannelFuture future;

    private final String host;
    private final Integer port;

    private final Request request;

    public RpcClientSocket(Request request) {
        this.request = request;
        this.host = request.getHost();
        this.port = request.getPort();
    }

    @Override
    public void run() {
        Long beatTime = Objects.isNull(request.getBeatIntervalTime()) || request.getBeatIntervalTime() <= 0 ?
                10 : request.getBeatIntervalTime();
        // 客户端只需要一个工作事件组来处理任务即可
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            // 选用客户端的启动器
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.AUTO_READ, true);
            b.option(ChannelOption.SO_KEEPALIVE, Boolean.TRUE);
            b.option(ChannelOption.TCP_NODELAY, Boolean.TRUE);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(
                            // 设定 IdleStateHandler 心跳检测 每 5 秒进行一次写检测
                            // write()方法超过 5 秒没调用，就调用 userEventTrigger
                            new IdleStateHandler(0, beatTime, 0, TimeUnit.SECONDS),
                            new RpcMessageDecoder(),
                            new RpcMessageEncoder(),
                            new ClientSocketHandler());
                }
            });
            // 进行服务器连接
            ChannelFuture f = b.connect(host, port).sync();
            this.future = f;
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

    public ChannelFuture getFuture() {
        return future;
    }

    public void setFuture(ChannelFuture future) {
        this.future = future;
    }
}
