package com.simple.rpc.core.compress;

import com.simple.rpc.common.interfaces.Compressor;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 不需要压缩，默认的，一些序列化工具就自带很好的压缩
 *
 * @author: WuChengXing
 * @create: 2022-05-02 12:09
 **/
public class DefaultCompressor implements Compressor {

    @Override
    public byte[] compress(byte[] bytes) {
        return bytes;
    }

    @Override
    public byte[] decompress(byte[] bytes) {
        return bytes;
    }
}
