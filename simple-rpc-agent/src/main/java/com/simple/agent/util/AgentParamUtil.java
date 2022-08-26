package com.simple.agent.util;

import com.alibaba.fastjson.JSON;
import com.simple.agent.entity.AgentParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 项目: class-byte-code
 * <p>
 * 功能描述: 参数处理工具
 *
 * @author: WuChengXing
 * @create: 2022-06-09 23:56
 **/
public class AgentParamUtil {

    /**
     * 处理参数以;组成的字符串
     *
     * @param param
     * @return
     */
    public static List<String> dealParam(String param) {
        if (Objects.isNull(param) || "".equals(param)) {
            return new ArrayList<>();
        }
        return new ArrayList<>(Arrays.asList(param.split(";")));
    }

    public static void main(String[] args) {
        AgentParam agentParam= new AgentParam();
        agentParam.setPlugins("trace");
        agentParam.setContainRules("01");
        agentParam.setIgnoreRules("04");
        agentParam.setClassPrefix("com.simple.rpc");
        agentParam.setIgnoreClassNames("p1");
        System.out.println(JSON.toJSONString(agentParam));
    }
}
