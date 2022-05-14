create table register_center
(
    register_key   varchar(400) null comment '注册key',
    host_port      varchar(64) null comment 'url地址信息',
    register_value varchar(4000) null comment '注册信息',
    id             bigint auto_increment,
    constraint register_center_pk
        primary key (id)
) comment '注册中心';

create
index idx_key_host_port on register_center (register_key, host_port);

