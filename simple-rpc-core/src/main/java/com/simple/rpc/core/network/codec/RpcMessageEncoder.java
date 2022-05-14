package com.simple.rpc.core.network.codec;

import com.simple.rpc.common.constant.MessageFormatConstant;
import com.simple.rpc.common.constant.enums.CompressType;
import com.simple.rpc.common.constant.enums.MessageType;
import com.simple.rpc.common.constant.enums.SerializeType;
import com.simple.rpc.common.interfaces.Compressor;
import com.simple.rpc.common.interfaces.Serializer;
import com.simple.rpc.core.network.message.RpcMessage;
import com.simple.rpc.common.spi.ExtensionLoader;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 这里是模仿 ccx-rpc的编解码
 * <p>
 * 自定义协议编码器
 * <p>
 * <pre>
 *   0     1     2       3    4    5    6    7           8        9        10   11   12   13   14   15   16   17   18
 *   +-----+-----+-------+----+----+----+----+-----------+---------+--------+----+----+----+----+----+----+----+---+
 *   |   magic   |version|    full length    |messageType|serialize|compress|              RequestId               |
 *   +-----+-----+-------+----+----+----+----+-----------+----- ---+--------+----+----+----+----+----+----+----+---+
 *   |                                                                                                             |
 *   |                                         body                                                                |
 *   |                                                                                                             |
 *   |                                        ... ...                                                              |
 *   +-------------------------------------------------------------------------------------------------------------+
 *   2B magic（魔数）
 *   1B version（版本）
 *   4B full length（消息长度）
 *   1B messageType（消息类型）
 *   1B serialize（序列化类型）
 *   1B compress（压缩类型）
 *   8B requestId（请求的Id）
 *   body（object类型数据）
 * </pre>
 *
 * @author: WuChengXing
 * @create: 2022-04-27 18:52
 **/
public class RpcMessageEncoder extends MessageToByteEncoder<RpcMessage> {

    @Override
    protected void encode(ChannelHandlerContext ctx, RpcMessage rpcMessage, ByteBuf out) {
        // 2B magic code（魔数）
        out.writeBytes(MessageFormatConstant.MAGIC);
        // 1B version（版本）
        out.writeByte(MessageFormatConstant.VERSION);
        // 4B full length（消息长度）. 总长度先空着，后面填。
        out.writerIndex(out.writerIndex() + MessageFormatConstant.FULL_LENGTH_LENGTH);
        // 1B messageType（消息类型）
        out.writeByte(rpcMessage.getMessageType());
        // 1B codec（序列化类型）
        out.writeByte(rpcMessage.getSerializeType());
        // 1B compress（压缩类型）
        out.writeByte(rpcMessage.getCompressTye());
        // 8B requestId（请求的Id）
        out.writeLong(rpcMessage.getRequestId());
        // 写 body，返回 body 长度
        int bodyLength = writeBody(rpcMessage, out);

        // 当前写指针
        int writerIndex = out.writerIndex();
        out.writerIndex(MessageFormatConstant.MAGIC_LENGTH + MessageFormatConstant.VERSION_LENGTH);
        // 4B full length（消息长度）
        out.writeInt(MessageFormatConstant.HEADER_LENGTH + bodyLength);
        // 写指针复原
        out.writerIndex(writerIndex);
    }

    /**
     * 写 body
     *
     * @return body 长度
     */
    private int writeBody(RpcMessage rpcMessage, ByteBuf out) {
        byte messageType = rpcMessage.getMessageType();
        // 如果是 ping、pong 心跳类型的，没有 body，直接返回头部长度
        if (messageType == MessageType.HEARTBEAT.getValue()) {
            return 0;
        }

        // 序列化器
        SerializeType serializeType = SerializeType.fromValue(rpcMessage.getSerializeType());
        if (serializeType == null) {
            throw new IllegalArgumentException("codec type not found");
        }
        Serializer serializer = ExtensionLoader.getLoader(Serializer.class).getExtension(serializeType.getName());
        // 压缩器
        CompressType compressType = CompressType.fromValue(rpcMessage.getCompressTye());
        if (compressType == null) {
            throw new IllegalArgumentException("compress type not found");
        }
        Compressor compressor = ExtensionLoader.getLoader(Compressor.class).getExtension(compressType.getName());
        // 序列化
        byte[] notCompressBytes = serializer.serialize(rpcMessage.getData());
        // 压缩
        byte[] compressedBytes = compressor.compress(notCompressBytes);

        // 写 body
        out.writeBytes(compressedBytes);
        return compressedBytes.length;
    }
}
