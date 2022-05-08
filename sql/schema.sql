drop schema if exists eatproject;
create schema eatproject;

use eatproject;

create table users(
    user_id char(8) primary key,
    username varchar(16) not null,
    password varchar(256)
);

create table comments(
    comment_id int auto_increment primary key,
    rating int,
    user_id char(8),
    text varchar(256),
    listing_id varchar(256),

    constraint fk_user_id_comments
        foreign key(user_id)
        references users(user_id)
);

create table history(
    search_id int auto_increment primary key,
    user_id char(8),
    keyword varchar(128),
    nextToken varchar(256),
    date date,

    constraint fk_user_id_history
        foreign key(user_id)
        references users(user_id)
)