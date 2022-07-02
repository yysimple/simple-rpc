package com.simple.statistic.service.trace;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

import com.simple.statistic.entity.*;
import com.simple.statistic.entity.statistic.StatisticTraceRequest;
import com.simple.statistic.entity.request.TraceSearchRequest;
import com.simple.statistic.entity.response.AgentLogView;
import com.simple.statistic.entity.statistic.StatisticTraceLog;
import com.simple.statistic.entity.response.TraceTreeView;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 日志链路监控表Service类
 *
 * @author: yysimple
 * @create: 2022-06-25 17:24:22
 **/
public interface SimpleAgentLogService extends IService<SimpleAgentLog> {
    /**
     * 通过ID查询单个日志链路监控表
     *
     * @param id ID
     * @return {@link SimpleAgentLog}
     */
    SimpleAgentLog findSimpleAgentLogById(Long id);

    /**
     * 多条件查询SimpleAgentLog列表
     *
     * @param simpleAgentLog
     * @return java.util.List<SimpleAgentLog>
     */
    List<SimpleAgentLog> listSimpleAgentLog(SimpleAgentLog simpleAgentLog);

    /**
     * 查询agent log列表；
     *
     * @param request
     * @return
     */
    Page<AgentLogView> listTrace(TraceSearchRequest request);

    /**
     * 拿到traceId对应的进入的链路详情
     *
     * @param traceId
     * @return
     */
    List<TraceTreeView> traceEntryTreeView(String traceId);

    /**
     * 拿到对应traceId退出方法的调用链路
     *
     * @param traceId
     * @return
     */
    List<TraceTreeView> traceExitTreeView(String traceId);

    /**
     * 根据id——traceId去查询方法进入和退出
     *
     * @param id
     * @return
     */
    SimpleAgentLog getSimpleAgentLog(Long id);

    /**
     * 统计调用状态
     *
     * @param request
     * @return
     */
    StatisticTraceLog statisticTraceLog(StatisticTraceRequest request);
}