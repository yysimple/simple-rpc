package com.simple.statistic.controller;

import javax.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.simple.statistic.entity.SimpleAgentLog;
import com.simple.statistic.entity.SimpleResponse;
import com.simple.statistic.service.trace.SimpleAgentLogService;

/**
* 项目: simple-rpc
*
* 功能描述: 日志链路监控表Controller类
*
* @author: yysimple
* @create: 2022-06-25 17:24:22
**/
@RestController
@RequestMapping("/simpleAgentLog")
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
    * 新增日志链路监控表
    *
    * @param simpleAgentLog 日志链路监控表
    */
    @PostMapping("/insertSimpleAgentLog")
    public SimpleResponse<String> insertSimpleAgentLog(@RequestBody SimpleAgentLog simpleAgentLog) {
        simpleAgentLogService.insertSimpleAgentLog(simpleAgentLog);
        return new SimpleResponse<>("insert success");
    }

    /**
    * 修改日志链路监控表
    *
    * @param simpleAgentLog 日志链路监控表
    */
    @PostMapping("/updateSimpleAgentLog")
    public SimpleResponse<String> updateSimpleAgentLog(@RequestBody SimpleAgentLog simpleAgentLog) {
        simpleAgentLogService.updateSimpleAgentLog(simpleAgentLog);
        return new SimpleResponse<>("update success");
    }

    /**
    * 通过ID删除单个日志链路监控表
    *
    * @param id ID
    */
    @PostMapping("/deleteSimpleAgentLogById")
    public SimpleResponse<String> deleteSimpleAgentLogById(@RequestParam("id") Long id) {
        simpleAgentLogService.deleteSimpleAgentLogById(id);
        return new SimpleResponse<>("delete success");
    }
}