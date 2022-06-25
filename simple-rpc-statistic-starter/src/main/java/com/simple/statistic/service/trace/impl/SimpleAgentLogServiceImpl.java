package com.simple.statistic.service.trace.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import javax.annotation.Resource;
import java.util.List;
import com.simple.statistic.entity.SimpleAgentLog;
import com.simple.statistic.mapper.SimpleAgentLogMapper;
import com.simple.statistic.service.trace.SimpleAgentLogService;

/**
* 项目: simple-rpc
*
* 功能描述: 日志链路监控表ServiceImpl类
*
* @author: yysimple
* @create: 2022-06-25 17:24:22
**/
@Service
public class SimpleAgentLogServiceImpl extends ServiceImpl<SimpleAgentLogMapper, SimpleAgentLog> implements SimpleAgentLogService {

    @Resource
    private SimpleAgentLogMapper simpleAgentLogMapper;

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

    /**
    * 新增日志链路监控表
    *
    * @param simpleAgentLog 日志链路监控表
    */
    @Override
    public void insertSimpleAgentLog(SimpleAgentLog simpleAgentLog) {
        simpleAgentLogMapper.insertSimpleAgentLog(simpleAgentLog);
    }

    /**
    * 修改日志链路监控表
    *
    * @param simpleAgentLog 日志链路监控表
    */
    @Override
    public void updateSimpleAgentLog(SimpleAgentLog simpleAgentLog) {
        simpleAgentLogMapper.updateSimpleAgentLog(simpleAgentLog);
    }

    /**
    * 通过ID删除单个日志链路监控表
    *
    * @param id ID
    */
    @Override
    public void deleteSimpleAgentLogById(Long id) {
        simpleAgentLogMapper.deleteSimpleAgentLogById(id);
    }
}