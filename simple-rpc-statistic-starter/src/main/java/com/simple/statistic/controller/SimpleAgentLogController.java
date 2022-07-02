package com.simple.statistic.controller;

import javax.annotation.Resource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.simple.statistic.entity.*;
import com.simple.statistic.entity.request.TraceSearchRequest;
import com.simple.statistic.entity.response.AgentLogView;
import com.simple.statistic.entity.response.SimpleResponse;
import com.simple.statistic.entity.response.TraceTreeView;
import com.simple.statistic.entity.statistic.StatisticTraceLog;
import com.simple.statistic.entity.statistic.StatisticTraceRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.simple.statistic.service.trace.SimpleAgentLogService;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 日志链路监控表Controller类
 *
 * @author: yysimple
 * @create: 2022-06-25 17:24:22
 **/
@RestController
@RequestMapping("/simpleAgentLog")
@CrossOrigin
public class SimpleAgentLogController {

    @Resource
    private SimpleAgentLogService simpleAgentLogService;

    /**
     * 通过ID查询单个日志链路监控表
     *
     * @param id ID
     * @return {@link SimpleAgentLog}
     */
    @GetMapping("/findSimpleAgentLogById")
    public SimpleResponse<SimpleAgentLog> findSimpleAgentLogById(@RequestParam("id") Long id) {
        return new SimpleResponse<>(simpleAgentLogService.findSimpleAgentLogById(id));
    }

    /**
     * 多条件查询SimpleAgentLog列表
     *
     * @param simpleAgentLog
     * @return
     */
    @PostMapping("/listSimpleAgentLog")
    public SimpleResponse<List<SimpleAgentLog>> listSimpleAgentLog(@RequestBody SimpleAgentLog simpleAgentLog) {
        return new SimpleResponse<>(simpleAgentLogService.listSimpleAgentLog(simpleAgentLog));
    }

    /**
     * 查询Trace列表
     *
     * @param traceSearchRequest
     * @return
     */
    @PostMapping("/listTrace")
    public SimpleResponse<Page<AgentLogView>> listTrace(@RequestBody TraceSearchRequest traceSearchRequest) {
        return new SimpleResponse<>(simpleAgentLogService.listTrace(traceSearchRequest));
    }

    @GetMapping("/traceEntryTreeView")
    public SimpleResponse<List<TraceTreeView>> traceEntryTreeView(String traceId) {
        return new SimpleResponse<>(simpleAgentLogService.traceEntryTreeView(traceId));
    }

    @GetMapping("/traceExitTreeView")
    public SimpleResponse<List<TraceTreeView>> traceExitTreeView(String traceId) {
        return new SimpleResponse<>(simpleAgentLogService.traceExitTreeView(traceId));
    }

    @GetMapping("/getSimpleAgentLog")
    public SimpleResponse<SimpleAgentLog> getSimpleAgentLog(Long id) {
        return new SimpleResponse<>(simpleAgentLogService.getSimpleAgentLog(id));
    }

    @PostMapping("/statisticTraceLog")
    public SimpleResponse<StatisticTraceLog> statisticTraceLog(@RequestBody StatisticTraceRequest request) {
        return new SimpleResponse<>(simpleAgentLogService.statisticTraceLog(request));
    }

}