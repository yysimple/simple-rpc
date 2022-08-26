package com.simple.agent.plugins.match.aggregation;

import cn.hutool.core.collection.CollectionUtil;
import com.simple.agent.entity.AgentParam;
import com.simple.agent.plugins.match.ClazzMatch;
import com.simple.agent.plugins.match.common.MultiNamedMatch;
import com.simple.agent.plugins.match.common.OrMatch;
import com.simple.agent.plugins.match.common.PrefixMatch;
import com.simple.agent.entity.MatchEnums;
import com.simple.agent.util.AgentParamUtil;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;

import java.util.ArrayList;
import java.util.Arrays;
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

    public static ElementMatcher.Junction buildAllMatch(AgentParam agentParam) {
        List<String> containRules = AgentParamUtil.dealParam(agentParam.getContainRules());
        if (CollectionUtil.isEmpty(containRules)) {
            return ElementMatchers.none();
        }
        List<ClazzMatch> clazzMatches = new ArrayList<>();
        containRules.forEach(r -> {
            if (MatchEnums.PREFIX.getCode().equals(r)) {
                List<String> classPrefix = AgentParamUtil.dealParam(agentParam.getClassPrefix());
                PrefixMatch match = PrefixMatch.nameStartsWith(classPrefix);
                clazzMatches.add(match);
            } else if (MatchEnums.NAMED.getCode().equals(r)) {
                List<String> className = AgentParamUtil.dealParam(agentParam.getClassNames());
                MultiNamedMatch match = MultiNamedMatch.byMultiClassMatch(className);
                clazzMatches.add(match);
            }
        });
        OrMatch or = OrMatch.or(clazzMatches);
        return or.orJunction();
    }

    public static ElementMatcher.Junction buildIgnoreMatch(AgentParam agentParam) {
        List<String> containRules = AgentParamUtil.dealParam(agentParam.getIgnoreRules());
        if (CollectionUtil.isEmpty(containRules)) {
            return ElementMatchers.named("agent");
        }
        List<ClazzMatch> clazzMatches = new ArrayList<>();
        containRules.forEach(r -> {
            if (MatchEnums.IGNORE_PREFIX.getCode().equals(r)) {
                List<String> classPrefix = AgentParamUtil.dealParam(agentParam.getIgnoreClassPrefix());
                PrefixMatch match = PrefixMatch.nameStartsWith(classPrefix);
                clazzMatches.add(match);
            } else if (MatchEnums.IGNORE_NAMED.getCode().equals(r)) {
                List<String> className = AgentParamUtil.dealParam(agentParam.getIgnoreClassNames());
                MultiNamedMatch match = MultiNamedMatch.byMultiClassMatch(className);
                clazzMatches.add(match);
            }
        });
        OrMatch or = OrMatch.or(clazzMatches);
        return or.orJunction();
    }
}
