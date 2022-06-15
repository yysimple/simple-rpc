package com.simple.agent.util;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-06-15 22:12
 **/
public class LogTest {
    public static void main(String[] args) {
        AgentLog.info("== info ==");
        AgentLog.debug("== debug ==");
        AgentLog.warn("== warn ==");
        AgentLog.error("== error ==");
    }
}
