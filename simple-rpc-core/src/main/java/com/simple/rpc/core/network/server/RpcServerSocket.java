package com.simple.rpc.core.network.server;

import com.simple.rpc.core.config.entity.LocalAddressInfo;
import com.simple.rpc.core.network.codec.RpcDecoder;
import com.simple.rpc.core.network.codec.RpcEncoder;
import com.simple.rpc.core.network.message.Request;
import com.simple.rpc.core.network.message.Response;
import com.simple.rpc.core.util.NetUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: rpc服务端基础配置
 *
 * @author: WuChengXing
 * @create: 2022-04-18 11:35
 **/
public class RpcServerSocket implements Runnable {

    private ChannelFuture f;

    public boolean isActiveSocketServer() {
        try {
            if (f != null) {
                return f.channel().isActive();
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void run() {
        // 这里如果自己不指定线程数，默认是当前cpu的两倍
        EventLoopGroup dealConnGroup = new NioEventLoopGroup(128);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(dealConnGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) {
                            ch.pipeline().addLast(
                                    new RpcDecoder(Request.class),
                                    new RpcEncoder(Response.class),
                                    new ServerSocketHandler());
                        }
                    });
            // 默认启动初始端口
            int port = 41200;
            while (NetUtil.isPortUsing(port)) {
                port++;
            }
            LocalAddressInfo.LOCAL_HOST = NetUtil.getHost();
            LocalAddressInfo.PORT = port;
            //注册服务
            this.f = bootstrap.bind(port).sync();
            // 返回请求等待结果，异步监听事件
            this.f.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
            dealConnGroup.shutdownGracefully();
        }
    }
}
