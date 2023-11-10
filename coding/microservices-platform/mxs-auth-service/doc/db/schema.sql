create table t_role
(
	id bigint(20) auto_increment primary key,
	version int(10) default 0 null,
	code varchar(255) null comment '权限编码',
	name varchar(255) null comment '权限名称',
    `desc` varchar(255) null comment '描述',
	create_by varchar(255) null,
	create_at timestamp default CURRENT_TIMESTAMP null,
	update_by varchar(255) null,
	update_at timestamp default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
	memo varchar(255) null
);

create table r_user_role
(
	id bigint(20) auto_increment primary key,
	version int(10) default 0 null,
	user_id int(10) null comment '用户id',
	role_id int(10) null comment '角色id',
	create_by varchar(255) null,
	create_at timestamp default CURRENT_TIMESTAMP null,
	update_by varchar(255) null,
	update_at timestamp default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
	memo varchar(255) null
);