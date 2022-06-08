--삭제
drop table board;
drop sequence seq_board_no;

--테이블 생성
create table board (
    no          number,
    title       varchar(500)    not null,
    content     varchar(4000),
    hit         number          default 0,
    reg_date    date            not null,
    user_no     number          not null,
    primary key (no),
    constraint board_fk foreign key (user_no)
    references users(no)
);

create sequence seq_board_no
increment by 1
start with 1;

--데이터 생성


--커밋
commit;