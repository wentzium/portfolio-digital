-- create database leica default charset utf8 collate utf8_general_ci;

-- create loginUser leica identified by 'leica123';

-- grant all privileges on leica.* to 'leica';

-- flush privileges;

use leica;


create table t_apply
(
	id int(20) auto_increment
		primary key,
	version int(10) default 0 null,
	code varchar(255) null comment '申请单编号',
	state varchar(255) null comment '申请单状态',
	create_by varchar(255) null,
	create_at timestamp default CURRENT_TIMESTAMP null,
	update_by varchar(255) null,
	update_at timestamp default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
	memo varchar(255) null
);