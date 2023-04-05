drop table if exists author;
create table author(id bigint primary key auto_increment, name varchar(255));

drop table if exists genre;
create table genre(id bigint primary key auto_increment, name varchar(255));

drop table if exists book;
create table book(id bigint primary key auto_increment, name varchar(255), authorid bigint, genreid bigint);

drop table if exists bookcomment;
create table bookcomment(id bigint primary key auto_increment, comment varchar(255), bookid bigint);

drop table if exists appuser;
create table appuser(
    id bigint primary key auto_increment,
    name varchar(255),
    password varchar(255),
    role varchar(32)
);
