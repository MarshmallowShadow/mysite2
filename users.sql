drop table users;
drop sequence seq_users_no;

create table users(
    no          number(10),
    id          varchar2(20)    not null unique,
    password    varchar2(20)    not null,
    name        varchar2(20),
    gender      varchar2(20),
    primary key (no)
);

create sequence seq_users_no
increment by 1
start with 1;

