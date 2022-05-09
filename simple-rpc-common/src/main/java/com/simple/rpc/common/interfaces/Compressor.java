package com.simple.rpc.common.interfaces;

import com.simple.rpc.common.annotation.SimpleRpcSPI;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 压缩类型
 *
 * @author: WuChengXing
 * @create: 2022-05-02 12:04
 **/
@SimpleRpcSPI(value = "default")
public interface Compressor {

    /**
     * 压缩
     *
     * @param bytes 压缩前的字节数组
     * @return 压缩后的字节数组
     */
    byte[] compress(byte[] bytes);

    /**
     * 解压
     *
     * @param bytes 解压前的字节数组
     * @return 解压后的字节数组
     */
    byte[] decompress(byte[] bytes);
}
