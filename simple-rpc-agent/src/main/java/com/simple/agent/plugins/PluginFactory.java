package com.simple.agent.plugins;


import com.simple.agent.constant.AgentConstant;
import com.simple.agent.entity.AgentParam;
import com.simple.agent.plugins.impl.trace.TracePlugin;
import com.simple.agent.util.AgentParamUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 项目: class-byte-code
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-06-03 00:47
 **/
public class PluginFactory {

    public static List<IPlugin> pluginGroup = new ArrayList<>();
    public static Map<String, IPlugin> PLUGIN_GROUPS = new ConcurrentHashMap<>(4);

    static {
        PLUGIN_GROUPS.put(AgentConstant.PLUGIN_TRACE, new TracePlugin());
    }

    public static List<IPlugin> listPlugins(AgentParam agentParam) {
        String plugins = agentParam.getPlugins();
        List<String> allPlugins = AgentParamUtil.dealParam(plugins);
        allPlugins.forEach(p -> {
            pluginGroup.add(PLUGIN_GROUPS.get(p));
        });
        return pluginGroup;
    }

    public static Boolean addPlugin(String pluginName, IPlugin plugin, boolean override) {
        if (override) {
            PLUGIN_GROUPS.put(pluginName, plugin);
        } else {
            PLUGIN_GROUPS.putIfAbsent(pluginName, plugin);
        }
        return true;
    }


}
