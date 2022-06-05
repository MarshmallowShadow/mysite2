drop table guestbook;
drop sequence seq_guestbook_no;

create table guestbook(
    no          number(10),
    name        varchar2(80)    not null unique,
    password    varchar2(20),
    content     varchar2(100),
    reg_date    date,
    primary key (no)
);

create sequence seq_guestbook_no;

insert into guestbook
values(777, 'test', '12345678', 'helllo', sysdate);

commit;

select * from guestbook;