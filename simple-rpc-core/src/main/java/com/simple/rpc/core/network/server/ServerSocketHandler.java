package com.simple.rpc.core.network.server;

import cn.hutool.core.collection.CollectionUtil;
import com.simple.rpc.common.constant.CommonConstant;
import com.simple.rpc.common.constant.SymbolConstant;
import com.simple.rpc.common.constant.enums.MessageType;
import com.simple.rpc.common.util.ClassLoaderUtils;
import com.simple.rpc.common.util.SimpleRpcLog;
import com.simple.rpc.core.filter.impl.FilterInvoke;
import com.simple.rpc.core.filter.impl.SpiLoadFilter;
import com.simple.rpc.common.cache.SimpleRpcServiceCache;
import com.simple.rpc.core.network.message.Request;
import com.simple.rpc.core.network.message.Response;
import com.simple.rpc.core.network.message.RpcMessage;
import com.simple.rpc.core.network.send.SyncWriteMap;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 服务端处理器
 *
 * @author: WuChengXing
 * @create: 2022-04-18 19:04
 **/
public class ServerSocketHandler extends SimpleChannelInboundHandler<RpcMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcMessage rpcMessage) throws Exception {
        try {
            // 不理心跳消息
            if (rpcMessage.getMessageType() != MessageType.REQUEST.getValue()) {
                return;
            }
            // 拿到请求参数
            Request msg = (Request) rpcMessage.getData();
            // 每次请求都进行缓存
            SyncWriteMap.SERVER_REQUEST.put(msg.getRequestId(), Thread.currentThread());
            //调用
            Class<?> classType = ClassLoaderUtils.forName(msg.getInterfaceName());
            List<String> paramTypes = msg.getParamTypes();
            Class[] transfer = new Class[paramTypes.size()];
            if (!CollectionUtil.isEmpty(paramTypes)) {
                for (int i = 0; i < paramTypes.size(); i++) {
                    transfer[i] = Class.forName(paramTypes.get(i));
                }
            }
            Method addMethod = classType.getMethod(msg.getMethodName(), transfer);
            // 从缓存中里面获取bean信息,并构建key
            String registerKey = CommonConstant.RPC_SERVICE_PREFIX +
                    SymbolConstant.UNDERLINE + msg.getInterfaceName() +
                    SymbolConstant.UNDERLINE + msg.getAlias();
            Object objectBean = SimpleRpcServiceCache.getService(registerKey);
            // todo 远程方法调用之前的初始化
            SpiLoadFilter.loadFilters();
            msg.setSimpleRpcContext(FilterInvoke.loadRemoteInvokeBeforeFilters(msg.getSimpleRpcContext()));
            // 进行反射调用
            Object result = addMethod.invoke(objectBean, msg.getArgs());
            //反馈
            Response response = new Response();
            response.setRequestId(rpcMessage.getRequestId());
            response.setResult(result);
            // 构建返回值
            RpcMessage responseRpcMsg = RpcMessage.copy(rpcMessage);
            responseRpcMsg.setMessageType(MessageType.RESPONSE.getValue());
            responseRpcMsg.setData(response);
            // 发送成功后移除当前请求
            ctx.writeAndFlush(responseRpcMsg).addListener((ChannelFutureListener) future -> {
                SyncWriteMap.SERVER_REQUEST.remove(msg.getRequestId());
            });
            //释放
            ReferenceCountUtil.release(msg);
        } catch (InvocationTargetException t) {
            Throwable e = t.getTargetException();
            // 异常的时候返回，终端客户端等待
            Response response = new Response();
            response.setRequestId(rpcMessage.getRequestId());
            response.setExceptionInfo(e.getMessage());
            // 构建返回值
            RpcMessage responseRpcMsg = RpcMessage.copy(rpcMessage);
            responseRpcMsg.setMessageType(MessageType.RESPONSE.getValue());
            responseRpcMsg.setData(response);
            ctx.writeAndFlush(responseRpcMsg);
            e.printStackTrace();
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        // 处理空闲状态的
        if (evt instanceof IdleStateEvent) {
            IdleState state = ((IdleStateEvent) evt).state();
            if (state == IdleState.READER_IDLE) {
                SimpleRpcLog.info("idle check happen, so close the connection");
                ctx.close();
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }
}
