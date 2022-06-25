use
    simple_collect_log;
create table simple_agent_log
(
    id             bigint auto_increment comment '主键',
    trace_id       varchar(64)   not null comment '链路的traceId',
    span_id        varchar(64)   not null comment '链路的spanId',
    parent_span_id varchar(64)   not null comment '链路的parentSpanId',
    level          int           not null comment '级别',
    entry_time     bigint        not null comment '方法进入时间',
    exit_time      bigint        not null comment '方法退出时间',
    app_name       varchar(64)   not null comment '应用名',
    host           varchar(128)  not null comment 'host',
    clazz_name     varchar(128)  not null comment '类名',
    method_name    varchar(64)   not null comment '方法名',
    request_info   varchar(4000) not null comment '请求信息',
    result_info    varchar(4000) not null comment '返回信息',
    exception_info varchar(4000) not null comment '异常信息',
    invoke_status  int           not null comment '调用状态：0-退出；1-调用',
    invoker        int           not null comment '是否是方法调用者：0-否；1-是',
    constraint simple_agent_log_pk primary key (id)
) comment '日志链路监控表';

create
    index idx_trace_id on simple_agent_log (trace_id);
create
    index idx_span_id on simple_agent_log (span_id);
create
    index idx_parent_span_id on simple_agent_log (parent_span_id);
create
    index idx_clazz_name on simple_agent_log (clazz_name);
create
    index idx_method_name on simple_agent_log (method_name);
create
    index idx_invoke_status on simple_agent_log (invoke_status);
create
    index idx_invoker on simple_agent_log (invoker);
