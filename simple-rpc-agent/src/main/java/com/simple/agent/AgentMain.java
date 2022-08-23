package com.simple.agent;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.simple.agent.constant.AgentConstant;
import com.simple.agent.entity.AgentParam;
import com.simple.agent.plugins.IPlugin;
import com.simple.agent.plugins.InterceptPoint;
import com.simple.agent.plugins.PluginFactory;
import com.simple.agent.util.AgentLog;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.utility.JavaModule;

import java.lang.instrument.Instrumentation;
import java.util.List;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-06-11 01:43
 **/
public class AgentMain {

    //JVM 首先尝试在代理类上调用以下方法
    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println(agentArgs);
        AgentLog.info("====== agent start =====");
        AgentBuilder agentBuilder = new AgentBuilder.Default();
        AgentParam agentParam = new AgentParam();
        if (!StrUtil.isBlank(agentArgs)) {
            agentParam = JSON.parseObject(agentArgs, AgentParam.class);
        } else {
            // 默认设置插件trace
            agentParam.setPlugins(AgentConstant.PLUGIN_TRACE);
            agentParam.setInterceptClassRule("com.simple.rpc");
        }
        List<IPlugin> pluginGroup = PluginFactory.listPlugins(agentParam);
        for (IPlugin plugin : pluginGroup) {
            List<InterceptPoint> interceptPoints = plugin.buildInterceptPoint();
            for (InterceptPoint point : interceptPoints) {
                AgentParam finalAgentParam = agentParam;
                AgentBuilder.Transformer transformer = (builder, typeDescription, classLoader, javaModule) -> {
                    builder = builder.visit(Advice.to(plugin.adviceClass()).on(point.buildMethodsMatcher(finalAgentParam)));
                    return builder;
                };
                agentBuilder = agentBuilder.type(point.buildTypesMatcher(agentParam)).transform(transformer).asDecorator();
            }
        }

        //监听
        AgentBuilder.Listener listener = new AgentBuilder.Listener() {
            @Override
            public void onDiscovery(String s, ClassLoader classLoader, JavaModule javaModule, boolean b) {

            }

            @Override
            public void onTransformation(TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule, boolean b, DynamicType dynamicType) {

            }

            @Override
            public void onIgnored(TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule, boolean b) {

            }

            @Override
            public void onError(String s, ClassLoader classLoader, JavaModule javaModule, boolean b, Throwable throwable) {

            }

            @Override
            public void onComplete(String s, ClassLoader classLoader, JavaModule javaModule, boolean b) {

            }

        };

        agentBuilder.with(listener).installOn(inst);

    }
}
