package com.simple.rpc.core.network.send;

import com.simple.rpc.core.network.message.Response;

import java.util.concurrent.Future;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 异步写接口
 *
 * @author: WuChengXing
 * @create: 2022-04-19 15:03
 **/
public interface WriteFuture<T> extends Future<T> {

    /**
     * 异常原因
     *
     * @return
     */
    Throwable cause();

    /**
     * 设置异常信息
     *
     * @param cause
     */
    void setCause(Throwable cause);

    /**
     * 是否写成功
     *
     * @return
     */
    boolean isWriteSuccess();

    /**
     * 设置写结果
     *
     * @param result
     */
    void setWriteResult(boolean result);

    /**
     * 请求id
     *
     * @return
     */
    long requestId();

    /**
     * 响应结果
     *
     * @return
     */
    T response();

    /**
     * 设置响应结果
     *
     * @param response
     */
    void setResponse(Response response);

    /**
     * 是否超时
     *
     * @return
     */
    boolean isTimeout();
}
