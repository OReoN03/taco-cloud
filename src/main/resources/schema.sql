drop table if exists taco_ingredients;
drop table if exists taco_order_tacos;
drop table if exists taco_seq;
drop table if exists taco_order_seq;

drop table if exists taco;
drop table if exists taco_order;
drop table if exists ingredient;

create table if not exists taco_order
(
    id              BIGINT      not null auto_increment primary key,
    delivery_Name   varchar(50) not null,
    delivery_Street varchar(50) not null,
    delivery_City   varchar(50) not null,
    delivery_State  varchar(2)  not null,
    delivery_Zip    varchar(10) not null,
    cc_number       varchar(16) not null,
    cc_expiration   varchar(5)  not null,
    cc_CVV          varchar(3)  not null,
    placed_at       timestamp   not null
);
create table if not exists taco
(
    id             BIGINT      not null auto_increment primary key,
    name           varchar(50) not null,
    created_at     timestamp   not null
);
create table if not exists ingredient
(
    id   varchar(4)  not null primary key,
    name varchar(25) not null,
    type varchar(10) not null
);
create table if not exists `user`
(
  id            BIGINT          not null auto_increment primary key,
  username      varchar(255)    not null,
  password      varchar(255)    not null,
  fullname      varchar(255)    not null,
  street        varchar(50)     not null,
  city          varchar(50)     not null,
  state         varchar(2)      not null,
  zip           varchar(10)     not null,
  phone_number  varchar(255)    not null
);
