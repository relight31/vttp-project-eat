drop schema if exists eatproject;
create schema eatproject;

use eatproject;

create table users(
    user_id char(8) primary key,
    username varchar(16) not null,
    password varchar(256)
);

create table comments(
    comment_id int auto_increment primary key
    rating int,
    user_id char(8),
    text varchar(256),
    listing_id varchar(256),

    constraint fk_user_id
        foreign key(user_id)
        references users(user_id)
);

create table history(
    user_id char(8),
    keyword varchar(128),
    nextToken varchar(256),

    constraint fk_user_id
        foreign key(user_id)
        references users(user_id)
)