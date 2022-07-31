create table if not exists body(
    id serial primary key,
    name varchar(50) not null
    );

create table if not exists brand(
    id serial primary key,
    name varchar(50) not null
    );

create table if not exists users(
    id serial primary key,
    name varchar(2000),
    password varchar(2000)
    );

create table if not exists categories (
    id serial primary key,
    name varchar(2000)
);

create table if not exists items(
       id serial primary key,
       price int,
       category_id int not null references categories(id),
       brand_id int not null references brand(id),
       body_id int not null references body(id),
       year int,
       description TEXT not null,
       photo bytea null,
       sale boolean,
       created  TIMESTAMP,
       user_id int not null references users(id)
);

ALTER TABLE users ADD CONSTRAINT name_unique UNIQUE (name);