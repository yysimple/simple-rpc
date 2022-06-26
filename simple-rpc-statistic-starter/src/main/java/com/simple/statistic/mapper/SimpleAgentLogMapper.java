package com.simple.statistic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.simple.statistic.entity.SimpleAgentLog;
import java.util.List;

/**
* 项目: simple-rpc
*
* 功能描述: 日志链路监控表
*
* @author: yysimple
* @create: 2022-06-25 17:24:22
**/
@Mapper
public interface SimpleAgentLogMapper extends BaseMapper<SimpleAgentLog> {

    /**
    * 通过ID查询单个日志链路监控表
    *
    * @param id ID
    * @return {@link SimpleAgentLog}
    */
    SimpleAgentLog findSimpleAgentLogById(@Param("id") Long id);

    /**
    * 多条件查询SimpleAgentLog列表
    *
    * @param simpleAgentLog
    * @return java.util.List<SimpleAgentLog>
    */
    List<SimpleAgentLog> listSimpleAgentLog(SimpleAgentLog simpleAgentLog);

}