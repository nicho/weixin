drop table if exists wx_task;
drop table if exists wx_user;
drop table if exists wx_weixinuser;

create table wx_task (
	id bigint auto_increment,
	title varchar(128) not null,
	description varchar(255),
	user_id bigint not null,
	image_url varchar(128) not null,
    primary key (id)
) engine=InnoDB;

create table wx_user (
	id bigint auto_increment,
	login_name varchar(64) not null unique,
	name varchar(64) not null,
	password varchar(255) not null,
	salt varchar(64) not null,
	roles varchar(255) not null,
	register_date timestamp not null default 0,
	primary key (id)
) engine=InnoDB;

CREATE TABLE wx_weixinuser (
	id BIGINT AUTO_INCREMENT,
	to_User_Name VARCHAR(64) NOT NULL,
	from_User_Name VARCHAR(64) NOT NULL,
	create_Time VARCHAR(64) NOT NULL,
	msg_Type VARCHAR(64) NOT NULL,
	event VARCHAR(255) NOT NULL,
	event_Key VARCHAR(255) NOT NULL,
	ticket VARCHAR(255) NOT NULL,
	create_Date TIMESTAMP NOT NULL DEFAULT 0,
	PRIMARY KEY (id)
) ENGINE=INNODB;