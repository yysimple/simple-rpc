package com.simple.rpc.core.network.codec;

import com.simple.rpc.common.constant.enums.CompressType;
import com.simple.rpc.common.constant.enums.MessageType;
import com.simple.rpc.common.constant.enums.SerializeType;
import com.simple.rpc.common.interfaces.Compressor;
import com.simple.rpc.common.interfaces.Serializer;
import com.simple.rpc.common.util.SimpleRpcLog;
import com.simple.rpc.core.network.message.Request;
import com.simple.rpc.core.network.message.Response;
import com.simple.rpc.core.network.message.RpcMessage;
import com.simple.rpc.common.spi.ExtensionLoader;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.util.Arrays;

import static com.simple.rpc.common.constant.MessageFormatConstant.*;


/**
 * 项目: simple-rpc
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-04-27 18:52
 **/
public class RpcMessageDecoder extends LengthFieldBasedFrameDecoder {

    public RpcMessageDecoder() {
        /**
         * 继承定长解码器，自己完成构造数据后，每次会将一次请求的消息一起解析，不会出现粘包、拆包
         * 这里的话假设数据是（二进制不补零）：52 1 0001 1 1 00000001 1
         * maxFrameLength： 8M
         * lengthFieldOffset：3
         * lengthFieldLength：
         * lengthAdjustment：-7
         * initialBytesToStrip：0
         *
         * 这里解析比较简单 首先是长度偏移量在 3字节后面，也就是跳过了 52 1；
         * 然后长度为 0001，对应4个字节；
         * 接下来就是调整消息读取位置，又到最前面去了，然后读取的初始位置就是0，从头还是读，所以52 1 0001 1 1 00000001 1都读取了
         */
        super(
                // 最大的长度，如果超过，会直接丢弃
                MAX_FRAME_LENGTH,
                // 描述长度的字段[4B full length（消息长度）]在哪个位置：在 [2B magic（魔数）]、[1B version（版本）] 后面
                MAGIC_LENGTH + VERSION_LENGTH,
                // 描述长度的字段[4B full length（消息长度）]本身的长度，也就是 4B 啦
                FULL_LENGTH_LENGTH,
                // LengthFieldBasedFrameDecoder 拿到消息长度之后，还会加上 [4B full length（消息长度）] 字段前面的长度
                // 因为我们的消息长度包含了这部分了，所以需要减回去
                -(MAGIC_LENGTH + VERSION_LENGTH + FULL_LENGTH_LENGTH),
                // initialBytesToStrip: 去除哪个位置前面的数据。因为我们还需要检测 魔数 和 版本号，所以不能去除
                0);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        Object decoded = super.decode(ctx, in);
        if (decoded instanceof ByteBuf) {
            ByteBuf frame = (ByteBuf) decoded;
            if (frame.readableBytes() >= HEADER_LENGTH) {
                try {
                    return decodeFrame(frame);
                } catch (Exception ex) {
                    SimpleRpcLog.error("Decode frame error.", ex);
                } finally {
                    frame.release();
                }
            }
        }
        return decoded;
    }

    /**
     * 业务解码
     */
    private RpcMessage decodeFrame(ByteBuf in) {
        readAndCheckMagic(in);
        readAndCheckVersion(in);
        int fullLength = in.readInt();
        byte messageType = in.readByte();
        byte codec = in.readByte();
        byte compress = in.readByte();
        long requestId = in.readLong();

        RpcMessage rpcMessage = new RpcMessage();
        rpcMessage.setMessageType(messageType);
        rpcMessage.setSerializeType(codec);
        rpcMessage.setRequestId(requestId);
        rpcMessage.setCompressTye(compress);

        if (messageType == MessageType.HEARTBEAT.getValue()) {
            return rpcMessage;
        }

        int bodyLength = fullLength - HEADER_LENGTH;
        if (bodyLength == 0) {
            return rpcMessage;
        }

        byte[] bodyBytes = new byte[bodyLength];
        in.readBytes(bodyBytes);
        // 解压
        CompressType compressType = CompressType.fromValue(compress);
        if (compressType == null) {
            throw new IllegalArgumentException("unknown compress type:" + codec);
        }
        Compressor compressor = ExtensionLoader.getLoader(Compressor.class).getExtension(compressType.getName());
        byte[] decompressedBytes = compressor.decompress(bodyBytes);

        // 反序列化
        SerializeType serializeType = SerializeType.fromValue(codec);
        if (serializeType == null) {
            throw new IllegalArgumentException("unknown codec type:" + codec);
        }
        Serializer serializer = ExtensionLoader.getLoader(Serializer.class).getExtension(serializeType.getName());
        Class<?> clazz = messageType == MessageType.REQUEST.getValue() ? Request.class : Response.class;
        Object object = serializer.deserialize(decompressedBytes, clazz);
        rpcMessage.setData(object);
        return rpcMessage;
    }

    /**
     * 读取并检查版本
     */
    private void readAndCheckVersion(ByteBuf in) {
        byte version = in.readByte();
        if (version != VERSION) {
            throw new IllegalArgumentException("Unknown version: " + version);
        }
    }

    /**
     * 读取并检查魔数
     */
    private void readAndCheckMagic(ByteBuf in) {
        byte[] bytes = new byte[MAGIC_LENGTH];
        in.readBytes(bytes);
        for (int i = 0; i < bytes.length; i++) {
            if (bytes[i] != MAGIC[i]) {
                throw new IllegalArgumentException("Unknown magic: " + Arrays.toString(bytes));
            }
        }
    }

}
