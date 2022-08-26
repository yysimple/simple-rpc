package com.simple.agent.plugins.impl.trace;

import com.simple.agent.entity.AgentParam;
import com.simple.agent.plugins.InterceptPoint;
import com.simple.agent.plugins.impl.rule.DefaultRules;
import com.simple.agent.plugins.match.aggregation.ClazzAggregationMatch;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;

/**
 * 项目: class-byte-code
 * <p>
 * 功能描述: 默认的trace拦截规则
 *
 * @author: WuChengXing
 * @create: 2022-06-09 23:48
 **/
public class DefaultTraceIntercept implements InterceptPoint {

    @Override
    public ElementMatcher<TypeDescription> buildTypesMatcher(AgentParam agentParam) {
        return ClazzAggregationMatch.buildAllMatch(agentParam)
                .and(ElementMatchers.not(ElementMatchers.nameStartsWith("com.simple.agent")))
                .and(DefaultRules.defaultIgnoreClass());
    }

    @Override
    public ElementMatcher<MethodDescription> buildMethodsMatcher(AgentParam agentParam) {
        return ElementMatchers.isMethod()
                .and(ElementMatchers.any())
                .and(DefaultRules.defaultIgnoreMethod());
    }
}
