package com.simple.rpc.core.network.client;

import com.simple.rpc.core.network.codec.RpcDecoder;
import com.simple.rpc.core.network.codec.RpcEncoder;
import com.simple.rpc.core.network.message.Request;
import com.simple.rpc.core.network.message.Response;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

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

    public RpcClientSocket(String host, Integer port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public void run() {
        // 客户端只需要一个工作事件组来处理任务即可
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            // 选用客户端的启动器
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.AUTO_READ, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(
                            new RpcDecoder(Response.class),
                            new RpcEncoder(Request.class),
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
