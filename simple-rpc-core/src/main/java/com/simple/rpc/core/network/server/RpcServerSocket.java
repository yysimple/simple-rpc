package com.simple.rpc.core.network.server;

import com.simple.rpc.common.util.NetUtil;
import com.simple.rpc.common.config.LocalAddressInfo;
import com.simple.rpc.core.network.codec.RpcMessageDecoder;
import com.simple.rpc.core.network.codec.RpcMessageEncoder;
import com.simple.rpc.core.network.message.Request;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

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

    private final Request request;

    public RpcServerSocket(Request request) {
        this.request = request;
    }

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
        Long stopConnectTime = Objects.isNull(request.getStopConnectTime()) || request.getStopConnectTime() <= 0 ?
                30 : request.getStopConnectTime();
        // 这里如果自己不指定线程数，默认是当前cpu的两倍
        EventLoopGroup dealConnGroup = new NioEventLoopGroup(128);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(dealConnGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    // 系统用于临时存放已完成三次握手的请求的队列的最大长度。如果连接建立频繁，服务器处理创建新连接较慢，可以适当调大这个参数
                    .option(ChannelOption.SO_BACKLOG, 128)
                    // 程序进程非正常退出，内核需要一定的时间才能够释放此端口，不设置 SO_REUSEADDR 就无法正常使用该端口。
                    .option(ChannelOption.SO_REUSEADDR, Boolean.TRUE)
                    // TCP/IP协议中针对TCP默认开启了Nagle 算法。
                    // Nagle 算法通过减少需要传输的数据包，来优化网络。在内核实现中，数据包的发送和接受会先做缓存，分别对应于写缓存和读缓存。
                    // 启动 TCP_NODELAY，就意味着禁用了 Nagle 算法，允许小包的发送。
                    // 对于延时敏感型，同时数据传输量比较小的应用，开启TCP_NODELAY选项无疑是一个正确的选择
                    .childOption(ChannelOption.TCP_NODELAY, Boolean.TRUE)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) {
                            ch.pipeline().addLast(
                                    // 30 秒之内没有收到客户端请求的话就关闭连接
                                    new IdleStateHandler(stopConnectTime, 0, 0, TimeUnit.SECONDS),
                                    new RpcMessageDecoder(),
                                    new RpcMessageEncoder(),
                                    new ServerSocketHandler());
                        }
                    });
            // 默认启动初始端口
            int port = Objects.isNull(request.getPort()) || request.getPort() <= 0 ? 41200 : request.getPort();
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
