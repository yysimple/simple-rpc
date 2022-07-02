package com.simple.statistic.service.trace.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.simple.statistic.config.StatisticProperties;
import com.simple.statistic.entity.statistic.StatisticTraceRequest;
import com.simple.statistic.entity.response.AgentLogView;
import com.simple.statistic.entity.request.TraceSearchRequest;
import com.simple.statistic.entity.statistic.StatisticTraceLog;
import com.simple.statistic.entity.response.TraceTreeView;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import com.simple.statistic.entity.SimpleAgentLog;
import com.simple.statistic.mapper.SimpleAgentLogMapper;
import com.simple.statistic.service.trace.SimpleAgentLogService;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 日志链路监控表ServiceImpl类
 *
 * @author: yysimple
 * @create: 2022-06-25 17:24:22
 **/
@Service
public class SimpleAgentLogServiceImpl extends ServiceImpl<SimpleAgentLogMapper, SimpleAgentLog> implements SimpleAgentLogService {

    @Resource
    private SimpleAgentLogMapper simpleAgentLogMapper;

    @Resource
    private StatisticProperties properties;

    /**
     * 通过ID查询单个日志链路监控表
     *
     * @param id ID
     * @return {@link SimpleAgentLog}
     */
    @Override
    public SimpleAgentLog findSimpleAgentLogById(Long id) {
        return simpleAgentLogMapper.findSimpleAgentLogById(id);
    }

    /**
     * 多条件查询SimpleAgentLog列表
     *
     * @param simpleAgentLog
     * @return java.util.List<SimpleAgentLog>
     */
    @Override
    public List<SimpleAgentLog> listSimpleAgentLog(SimpleAgentLog simpleAgentLog) {
        return simpleAgentLogMapper.listSimpleAgentLog(simpleAgentLog);
    }

    @Override
    public Page<AgentLogView> listTrace(TraceSearchRequest request) {
        Integer pageNum = request.getPageNum();
        Integer pageSize = request.getPageSize();
        if (Objects.isNull(pageNum) || pageNum == 0) {
            pageNum = 1;
        }
        if (Objects.isNull(pageSize) || pageSize == 0) {
            pageSize = 10;
        }
        Page<SimpleAgentLog> simpleAgentLogIPage = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<SimpleAgentLog> lambdaQueryWrapper = Wrappers.lambdaQuery();
        if (!StrUtil.isBlank(request.getClazzName())) {
            lambdaQueryWrapper.eq(SimpleAgentLog::getClazzName, request.getClazzName());
        }
        if (!StrUtil.isBlank(request.getMethodName())) {
            lambdaQueryWrapper.eq(SimpleAgentLog::getMethodName, request.getMethodName());
        }
        if (!Objects.isNull(request.getStartTime()) && !Objects.isNull(request.getEndTime())) {
            lambdaQueryWrapper.between(SimpleAgentLog::getEntryTime, request.getStartTime(), request.getEndTime());
        }

        // invoker = 1代表是方法调用发起者
        lambdaQueryWrapper.eq(SimpleAgentLog::getInvoker, 1);
        IPage<SimpleAgentLog> page = simpleAgentLogMapper.selectPage(simpleAgentLogIPage, lambdaQueryWrapper);
        long count = this.count(lambdaQueryWrapper);
        List<AgentLogView> collect = page.getRecords().stream().map(this::calcProp).collect(Collectors.toList());
        Page<AgentLogView> returnPage = new Page<>();
        BeanUtils.copyProperties(page, returnPage);
        returnPage.setRecords(collect);
        returnPage.setTotal(count);
        return returnPage;
    }

    private AgentLogView calcProp(SimpleAgentLog log) {
        AgentLogView agentLogView = new AgentLogView();
        agentLogView.setTraceId(log.getTraceId());
        agentLogView.setInvokeTime(new Date(log.getEntryTime()));
        LambdaQueryWrapper<SimpleAgentLog> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SimpleAgentLog::getTraceId, log.getTraceId());
        List<SimpleAgentLog> list = this.list(lambdaQueryWrapper);
        agentLogView.setInvokeMethodNum((int) list.stream().filter(s -> s.getInvokeStatus() == 1).count());
        String spanId = list.stream().map(SimpleAgentLog::getSpanId).max(Comparator.comparing(String::length)).orElse("");
        int deep = 0;
        if (spanId.length() > 1) {
            deep = spanId.split("\\.").length;
        }
        agentLogView.setInvokeDeep(deep);
        List<SimpleAgentLog> collect = list.stream().filter(s -> !StrUtil.isBlank(s.getExceptionInfo())).collect(Collectors.toList());
        agentLogView.setHealth(CollectionUtil.isEmpty(collect) ? 1 : 0);
        List<SimpleAgentLog> firstEntry = list.stream().filter(s -> s.getInvoker() == 1).collect(Collectors.toList());
        Long finalExitTime = list.stream().filter(s -> s.getInvokeStatus() == 0).map(SimpleAgentLog::getExitTime).max(Comparator.comparing(Long::new)).orElse(0L);
        SimpleAgentLog simpleAgentLog = firstEntry.get(0);
        Long fullTime = finalExitTime - simpleAgentLog.getEntryTime();
        agentLogView.setFullTime(fullTime);
        return agentLogView;
    }

    @Override
    public List<TraceTreeView> traceEntryTreeView(String traceId) {
        LambdaQueryWrapper<SimpleAgentLog> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(SimpleAgentLog::getTraceId, traceId)
                .eq(SimpleAgentLog::getInvokeStatus, 1);
        List<SimpleAgentLog> list = this.list(queryWrapper);
        List<TraceTreeView> collect = list.stream().map(this::log2View).collect(Collectors.toList());
        return listEntryTree(collect);
    }

    @Override
    public List<TraceTreeView> traceExitTreeView(String traceId) {
        LambdaQueryWrapper<SimpleAgentLog> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(SimpleAgentLog::getTraceId, traceId)
                .eq(SimpleAgentLog::getInvokeStatus, 0);
        List<SimpleAgentLog> list = this.list(queryWrapper);
        List<TraceTreeView> collect = list.stream().map(this::log2View).collect(Collectors.toList());
        return listExitTree(collect);
    }

    private TraceTreeView log2View(SimpleAgentLog log) {
        TraceTreeView traceTreeView = new TraceTreeView();
        traceTreeView.setId(log.getId());
        traceTreeView.setTraceId(log.getTraceId());
        traceTreeView.setSpanId(log.getSpanId());
        traceTreeView.setClazzName(log.getClazzName());
        traceTreeView.setMethodName(log.getMethodName());
        traceTreeView.setParentSpanId(log.getParentSpanId());
        traceTreeView.setShowTitle(log.getClazzName() + " --> " + log.getMethodName());
        return traceTreeView;
    }

    private List<TraceTreeView> listEntryTree(List<TraceTreeView> traceTreeViews) {
        return traceTreeViews.stream()
                .filter(traceTreeView -> "".equals(traceTreeView.getParentSpanId()))
                .peek(menu -> menu.setChildren(getEntryChildren(menu, traceTreeViews))).collect(Collectors.toList());
    }

    private List<TraceTreeView> listExitTree(List<TraceTreeView> traceTreeViews) {
        return traceTreeViews.stream()
                .filter(traceTreeView -> "".equals(traceTreeView.getSpanId()))
                .peek(menu -> menu.setChildren(getExitChildren(menu, traceTreeViews))).collect(Collectors.toList());
    }

    private List<TraceTreeView> getEntryChildren(TraceTreeView root, List<TraceTreeView> all) {
        return all.stream().
                filter(traceTreeView -> traceTreeView.getParentSpanId().equals(root.getSpanId()))
                .peek(traceTreeView -> traceTreeView.setChildren(getEntryChildren(traceTreeView, all)))
                .collect(Collectors.toList());
    }

    private List<TraceTreeView> getExitChildren(TraceTreeView root, List<TraceTreeView> all) {
        return all.stream().
                filter(traceTreeView -> traceTreeView.getSpanId().equals(root.getParentSpanId()))
                .peek(traceTreeView -> traceTreeView.setChildren(getExitChildren(traceTreeView, all)))
                .collect(Collectors.toList());
    }

    @Override
    public SimpleAgentLog getSimpleAgentLog(Long id) {
        SimpleAgentLog resLog = this.getById(id);
        if (Objects.isNull(resLog)) {
            return null;
        }
        LambdaQueryWrapper<SimpleAgentLog> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(SimpleAgentLog::getTraceId, resLog.getTraceId())
                .eq(SimpleAgentLog::getClazzName, resLog.getClazzName())
                .eq(SimpleAgentLog::getMethodName, resLog.getMethodName())
                .eq(SimpleAgentLog::getInvokeStatus, 0);
        SimpleAgentLog exitLog = this.getOne(queryWrapper);
        resLog.setExitTime(exitLog.getExitTime());
        resLog.setResultInfo(exitLog.getResultInfo());
        resLog.setExceptionInfo(exitLog.getExceptionInfo());
        return resLog;
    }

    @Override
    public StatisticTraceLog statisticTraceLog(StatisticTraceRequest request) {
        StatisticTraceLog statisticTraceLog = new StatisticTraceLog();
        List<SimpleAgentLog> simpleAgentLogs = simpleAgentLogMapper.listStatisticData(request);
        // 设置请求次数
        statisticTraceLog.setRequestNum((long) simpleAgentLogs.size());
        // 设置异常调用数量
        List<String> collect = simpleAgentLogs.stream().map(SimpleAgentLog::getTraceId).collect(Collectors.toList());
        long exceptionNum = simpleAgentLogMapper.totalExceptionNum(collect);
        statisticTraceLog.setExceptionNum(exceptionNum);
        // 统计慢接口数量
        long slowApiNum = simpleAgentLogMapper.totalSlowApiNum(collect, Objects.isNull(properties.getSlowTime()) ? 10*1000L : properties.getSlowTime());
        statisticTraceLog.setShowApiNum(slowApiNum);
        //
        return statisticTraceLog;
    }


}