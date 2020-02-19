--
-- user table
create table if not exists user
(
    id       int                not null auto_increment primary key,

    username varchar(32) unique not null,
    email    varchar(64) unique not null,
    password varchar(64),
    nickname varchar(32),

    op_time  timestamp          not null default current_timestamp on update current_timestamp
) ENGINE = MyISAM
  DEFAULT CHARSET = utf8mb4;

insert into user (username, email, password, nickname)
select 'admin', 'admin@chobit.org', 'e2587017b295aad7a0b339c72d075e49', 'long'
from dual
where not exists(select 1 from user where username = 'admin');

--
-- meta data
create table if not exists meta
(
    id      int                not null auto_increment primary key,

    parent  int                not null default 0,

    type    varchar(16),
    name    varchar(64),
    slug    varchar(32) unique not null,
    remark  varchar(128),

    sn      int                         default 0,

    op_time timestamp          not null default current_timestamp on update current_timestamp

) ENGINE = MyISAM
  DEFAULT CHARSET = utf8mb4;

insert into meta (type, name, slug)
select 'CATEGORY', '默认', 'default'
from dual
where not exists(select 1 from meta where slug = 'default');


--
-- settings table
create table if not exists setting
(
    id      int         not null auto_increment primary key,

    name    varchar(64) not null unique,
    value   varchar(128),

    op_time timestamp   not null default current_timestamp on update current_timestamp
) ENGINE = MyISAM
  DEFAULT CHARSET = utf8mb4;

insert into setting (name, value)
select 'name', 'Calf'
from dual
where not exists(select 1 from setting where name = 'name');

insert into setting (name, value)
select 'notice', '这是一条测试通知'
from dual
where not exists(select 1 from setting where name = 'notice');


-- media
create table if not exists media
(
    id      int       not null auto_increment primary key,

    type    varchar(16),
    name    varchar(32),
    path    varchar(128),

    op_time timestamp not null default current_timestamp on update current_timestamp
) ENGINE = MyISAM
  DEFAULT CHARSET = utf8mb4;


-- authors
create table if not exists author
(
    id      int       not null auto_increment primary key,

    name    varchar(64),
    country varchar(32),
    bio     mediumtext,

    op_time timestamp not null default current_timestamp on update current_timestamp
) ENGINE = MyISAM
  DEFAULT CHARSET = utf8mb4;



insert into author (name, country)
select '未知', '未知'
from dual
where not exists(select 1 from author where name = '未知');


-- works
create table if not exists work
(
    id          int       not null auto_increment primary key,

    author_id   int                default 1,
    category_id int,
    cover       varchar(128),
    file        varchar(128),

    name        varchar(64),
    brief       tinytext,

    op_time     timestamp not null default current_timestamp on update current_timestamp
) ENGINE = MyISAM
  DEFAULT CHARSET = utf8mb4;


-- volume
create table if not exists volume
(
    id      int       not null auto_increment primary key,

    work_id int,
    name    varchar(64),

    op_time timestamp not null default current_timestamp on update current_timestamp
) ENGINE = MyISAM
  DEFAULT CHARSET = utf8mb4;


-- chapter
create table if not exists chapter
(
    id        int       not null auto_increment primary key,

    volume_id int                default 0,
    work_id   int,

    name      varchar(64),
    keywords varchar(64),
    content   mediumtext,

    op_time   timestamp not null default current_timestamp on update current_timestamp
) ENGINE = MyISAM
  DEFAULT CHARSET = utf8mb4;


-- feature
create table if not exists feature
(
    id        int                not null auto_increment primary key,

    cover     varchar(128),
    name      varchar(64) unique not null,
    alias     varchar(16),
    keywords varchar(64),
    brief     tinytext,

    op_time   timestamp          not null default current_timestamp on update current_timestamp
) ENGINE = MyISAM
  DEFAULT CHARSET = utf8mb4;

insert into feature (name, alias, keywords)
select '推荐', 'recommend', '推荐'
from dual
where not exists(select 1 from feature where name = '推荐');


-- feature-work
create table if not exists feature_record
(
    id         int       not null auto_increment primary key,
    type       tinyint            default 1,
    feature_id int,
    work_id    int,

    op_time    timestamp not null default current_timestamp on update current_timestamp
) ENGINE = MyISAM
  DEFAULT CHARSET = utf8mb4;


-- remote code
create table if not exists remote_code
(
    id      int       not null auto_increment primary key,
    user_id int,
    code    varchar(32),
    op_time timestamp not null default current_timestamp on update current_timestamp
) ENGINE = MyISAM
  DEFAULT CHARSET = utf8mb4;

