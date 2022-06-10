package com.simple.agent.entity;

/**
 * 项目: class-byte-code
 * <p>
 * 功能描述: agent参数
 *
 * @author: WuChengXing
 * @create: 2022-06-09 21:04
 **/
public class AgentParam {

    /**
     * 插件类型；jvm;trace;sql
     */
    private String plugins;

    /**
     * 规则相关  com.simple.agent
     */
    private String interceptClassRule;
    private String ignoreClassRule;

    /**
     * main
     */
    private String interceptMethodRule;
    private String ignoreMethodRule;

    public String getPlugins() {
        return plugins;
    }

    public void setPlugins(String plugins) {
        this.plugins = plugins;
    }

    public String getInterceptClassRule() {
        return interceptClassRule;
    }

    public void setInterceptClassRule(String interceptClassRule) {
        this.interceptClassRule = interceptClassRule;
    }

    public String getIgnoreClassRule() {
        return ignoreClassRule;
    }

    public void setIgnoreClassRule(String ignoreClassRule) {
        this.ignoreClassRule = ignoreClassRule;
    }

    public String getInterceptMethodRule() {
        return interceptMethodRule;
    }

    public void setInterceptMethodRule(String interceptMethodRule) {
        this.interceptMethodRule = interceptMethodRule;
    }

    public String getIgnoreMethodRule() {
        return ignoreMethodRule;
    }

    public void setIgnoreMethodRule(String ignoreMethodRule) {
        this.ignoreMethodRule = ignoreMethodRule;
    }

    @Override
    public String toString() {
        return "AgentParam{" +
                "plugins='" + plugins + '\'' +
                ", interceptClassRule='" + interceptClassRule + '\'' +
                ", ignoreClassRule='" + ignoreClassRule + '\'' +
                ", interceptMethodRule='" + interceptMethodRule + '\'' +
                ", ignoreMethodRule='" + ignoreMethodRule + '\'' +
                '}';
    }
}
