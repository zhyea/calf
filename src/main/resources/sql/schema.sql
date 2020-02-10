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
select 'admin', 'admin@chobit.org', '81b1c925de908ce13eaf44c5b9bbe6f0', 'long'
from dual
where not exists(select 1 from user where username = 'admin');

--
-- meta data
create table if not exists meta
(
    id      int       not null auto_increment primary key,

    parent  int       not null default 0,

    type    varchar(16),
    name    varchar(64),
    slug    varchar(32),
    remark  varchar(128),

    sn      int                default 0,

    op_time timestamp not null default current_timestamp on update current_timestamp

) ENGINE = MyISAM
  DEFAULT CHARSET = utf8mb4;

insert into meta (type, name, slug)
select 'CATEGORY', '默认', 'default'
from dual
where not exists(select 1 from meta where slug = 'default');


--
-- settings table
create table if not exists settings
(
    id      int         not null auto_increment primary key,

    name    varchar(64) not null unique,
    value   varchar(128),

    op_time timestamp   not null default current_timestamp on update current_timestamp
) ENGINE = MyISAM
  DEFAULT CHARSET = utf8mb4;

insert into settings (name, value)
select 'site_name', 'Buffalo'
from dual
where not exists(select 1 from settings where name = 'site_name');

insert into settings (name, value)
select 'notice', '这是一条测试通知'
from dual
where not exists(select 1 from settings where name = 'notice');


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
    key_words varchar(64),
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
    key_words varchar(64),
    brief     tinytext,

    op_time   timestamp          not null default current_timestamp on update current_timestamp
) ENGINE = MyISAM
  DEFAULT CHARSET = utf8mb4;

insert into feature (name, alias, key_words)
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

