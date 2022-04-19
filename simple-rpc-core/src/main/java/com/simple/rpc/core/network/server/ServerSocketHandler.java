package com.simple.rpc.core.network.server;

import com.simple.rpc.core.network.message.Request;
import com.simple.rpc.core.network.message.Response;
import com.simple.rpc.core.util.ClassLoaderUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Method;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 服务端处理器
 *
 * @author: WuChengXing
 * @create: 2022-04-18 19:04
 **/
public class ServerSocketHandler extends ChannelInboundHandlerAdapter {

    private ApplicationContext applicationContext;

    public ServerSocketHandler(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object obj) {
        try {
            Request msg = (Request) obj;
            //调用
            Class<?> classType = ClassLoaderUtils.forName(msg.getInterfaceName());
            Method addMethod = classType.getMethod(msg.getMethodName(), msg.getParamTypes());
            // 从spring里面获取bean信息
            Object objectBean = applicationContext.getBean(msg.getRef());
            // 进行反射调用
            Object result = addMethod.invoke(objectBean, msg.getArgs());
            //反馈
            Response request = new Response();
            request.setRequestId(msg.getRequestId());
            request.setResult(result);
            ctx.writeAndFlush(request);
            //释放
            ReferenceCountUtil.release(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }
}
