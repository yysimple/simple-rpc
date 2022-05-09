package com.simple.rpc.core.network.codec;

import com.simple.rpc.common.util.SerializationUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 解码器
 *
 * @author: WuChengXing
 * @create: 2022-04-18 16:32
 **/
@Deprecated
public class RpcDecoder extends ByteToMessageDecoder {

    private Class<?> genericClass;

    public RpcDecoder(Class<?> genericClass) {
        this.genericClass = genericClass;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        // 可读取的字节数小于4字节，则不处理
        if (in.readableBytes() < 4) {
            return;
        }
        /**
         * 指定字节数传输，这里跟NIO的byteBuffer有一些不同，这里只要是 读指针、写指针、可丢弃容量、剩余可用容量
         * 根据这些，来进行按指定缓存大小读取数据
         *
         */
        in.markReaderIndex();
        int dataLength = in.readInt();
        if (in.readableBytes() < dataLength) {
            in.resetReaderIndex();
            return;
        }
        byte[] data = new byte[dataLength];
        in.readBytes(data);
        // 序列化
        out.add(SerializationUtil.deserialize(data, genericClass));
    }
}
