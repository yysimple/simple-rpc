package com.simple.statistic.service.trace;

import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;
import com.simple.statistic.entity.SimpleAgentLog;

/**
* 项目: simple-rpc
*
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
    * 新增日志链路监控表
    *
    * @param simpleAgentLog 日志链路监控表
    */
    void insertSimpleAgentLog(SimpleAgentLog simpleAgentLog);

    /**
    * 修改日志链路监控表
    *
    * @param simpleAgentLog 日志链路监控表
    */
    void updateSimpleAgentLog(SimpleAgentLog simpleAgentLog);

    /**
    * 通过ID删除单个日志链路监控表
    *
    * @param id ID
    */
    void deleteSimpleAgentLogById(Long id);


}