package com.simple.agent.plugins.impl.trace;


import com.simple.agent.constant.AgentConstant;
import com.simple.agent.plugins.IPlugin;
import com.simple.agent.plugins.InterceptPoint;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目: class-byte-code
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-06-03 00:48
 **/
public class TracePlugin implements IPlugin {

    @Override
    public String name() {
        return AgentConstant.PLUGIN_TRACE;
    }

    @Override
    public List<InterceptPoint> buildInterceptPoint() {
        List<InterceptPoint> interceptPoints = new ArrayList<>();
        interceptPoints.add(new DefaultTraceIntercept());
        return interceptPoints;
    }

    @Override
    public Class adviceClass() {
        return TraceAdvice.class;
    }
}
