create table register_center
(
    register_key   varchar(400) null comment '注册key',
    register_value varchar(4000) null comment '注册信息',
    id             bigint auto_increment,
    constraint register_center_pk
        primary key (id)
) comment '注册中心';

create
unique index register_center_register_key_uindex
	on register_center (register_key);

