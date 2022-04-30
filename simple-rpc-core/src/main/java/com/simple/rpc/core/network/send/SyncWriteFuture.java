package com.simple.rpc.core.network.send;

import com.simple.rpc.core.network.message.Response;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 同步写
 *
 * @author: WuChengXing
 * @create: 2022-04-19 15:06
 **/
public class SyncWriteFuture implements WriteFuture<Response> {

    /**
     * 这个用来控制同步写
     */
    private CountDownLatch latch = new CountDownLatch(1);
    private final long begin = System.currentTimeMillis();
    private long timeout;
    private Response response;
    private final long requestId;
    private boolean writeResult;
    private Throwable cause;
    private boolean isTimeout = false;

    public SyncWriteFuture(long requestId) {
        this.requestId = requestId;
    }

    public SyncWriteFuture(long requestId, long timeout) {
        this.requestId = requestId;
        this.timeout = timeout;
        writeResult = true;
        isTimeout = false;
    }


    @Override
    public Throwable cause() {
        return cause;
    }

    @Override
    public void setCause(Throwable cause) {
        this.cause = cause;
    }

    @Override
    public boolean isWriteSuccess() {
        return writeResult;
    }

    @Override
    public void setWriteResult(boolean result) {
        this.writeResult = result;
    }

    @Override
    public long requestId() {
        return requestId;
    }

    @Override
    public Response response() {
        return response;
    }

    @Override
    public void setResponse(Response response) {
        this.response = response;
        // 设置好了响应值之后，这里释放锁
        latch.countDown();
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return true;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return false;
    }

    /**
     * 这里获取值的时候应该进行阻塞，等待上面的 latch锁释放资源，也即设置好了响应值之后
     *
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     */
    @Override
    public Response get() throws InterruptedException, ExecutionException {
        latch.await();
        return response;
    }

    @Override
    public Response get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        if (latch.await(timeout, unit)) {
            return response;
        }
        return null;
    }

    @Override
    public boolean isTimeout() {
        if (isTimeout) {
            return true;
        }
        return System.currentTimeMillis() - begin > timeout;
    }
}
