-- liquibase formatted sql

-- changeset nikitamavrodiy:1

create table users
(
    id         bigserial primary key,
    first_name varchar(50),
    last_name  varchar(50),
    patronymic varchar(50),
    birthday   date,
    email      varchar(50),
    phone      varchar(50),
    avatar     bytea
);