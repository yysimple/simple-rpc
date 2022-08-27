package com.simple.agent;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.simple.agent.constant.AgentConstant;
import com.simple.agent.entity.AgentParam;
import com.simple.agent.plugins.IPlugin;
import com.simple.agent.plugins.InterceptPoint;
import com.simple.agent.plugins.PluginFactory;
import com.simple.agent.util.AgentLog;
import com.simple.agent.util.AgentParamUtil;
import com.simple.rpc.common.config.ConfigManager;
import com.simple.rpc.common.interfaces.ConfigLoader;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;

import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;
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

    /**
     * JVM 首先尝试在代理类上调用以下方法
     *
     * @param agentArgs
     * @param inst
     */
    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println(agentArgs);
        AgentLog.info("====== agent start =====");
        AgentBuilder agentBuilder = new AgentBuilder.Default();
        AgentParam agentParam = AgentParamUtil.dealAgentParam(agentArgs);
        System.out.println("最后参数：" + JSON.toJSONString(agentParam));
        List<IPlugin> pluginGroup = PluginFactory.listPlugins(agentParam);
        for (IPlugin plugin : pluginGroup) {
            List<InterceptPoint> interceptPoints = plugin.buildInterceptPoint();
            for (InterceptPoint point : interceptPoints) {
                agentBuilder.type(point.buildTypesMatcher(agentParam))
                        .transform(new Transformer(plugin, point, agentParam))
                        .with(AgentBuilder.RedefinitionStrategy.RETRANSFORMATION)
                        .with(new Listener())
                        .installOn(inst);
            }
        }
    }

    private static class Transformer implements AgentBuilder.Transformer {

        IPlugin plugin;
        InterceptPoint interceptPoint;
        AgentParam agentParam;

        Transformer(IPlugin plugin, InterceptPoint interceptPoint, AgentParam agentParam) {
            this.plugin = plugin;
            this.interceptPoint = interceptPoint;
            this.agentParam = agentParam;
        }

        @Override
        public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder, TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule, ProtectionDomain protectionDomain) {
            return builder.visit(Advice.to(plugin.adviceClass()).on(interceptPoint.buildMethodsMatcher(agentParam)));
        }
    }

    private static class Listener implements AgentBuilder.Listener {

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
    }
}
