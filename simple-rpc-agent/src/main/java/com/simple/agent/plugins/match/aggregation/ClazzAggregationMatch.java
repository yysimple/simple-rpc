package com.simple.agent.plugins.match.aggregation;

import cn.hutool.core.util.StrUtil;
import com.simple.agent.entity.AgentParam;
import com.simple.agent.plugins.match.entity.MatchRule;
import com.simple.agent.util.AgentParamUtil;
import net.bytebuddy.matcher.ElementMatcher;

import java.util.List;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 聚合匹配
 *
 * @author: WuChengXing
 * @create: 2022-08-26 14:05
 **/
public class ClazzAggregationMatch {

    public static ElementMatcher.Junction buildMatch(MatchRule rule, AgentParam agentParam) {
        List<String> rules = AgentParamUtil.dealParam(rule.getRules());
        return null;
    }
}
