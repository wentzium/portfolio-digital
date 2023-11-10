create database leica default charset utf8 collate utf8_general_ci;

create loginUser leica identified by 'leica123';

grant all privileges on leica.* to 'leica';

flush privileges;

use leica;


create table loginUser
(
	id int(20) auto_increment
		primary key,
	version int(10) default 0 null,
	nickName varchar(255) null,
	username varchar(255) null,
	password varchar(255) null,
	salt varchar(255) null,
	uuid varchar(255) null,
	last_login_time timestamp null,
	create_by varchar(255) null,
	create_at timestamp default CURRENT_TIMESTAMP null,
	update_by varchar(255) null,
	update_at timestamp default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
	memo varchar(255) null
);