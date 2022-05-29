create table if not exists body(
    id serial primary key,
    name varchar(50) not null
    );

create table if not exists model(
    id serial primary key,
    name varchar(50) not null
    );

create table if not exists posts(
    id serial primary key,
    model_id int not null references model(id),
    body_id int not null references body(id),
    description TEXT not null,
    photo bytea null,
    sale boolean,
    created  TIMESTAMP
    );

create table if not exists users(
    id serial primary key,
    name varchar(2000),
    password varchar(2000),
    item_id int not null references users(id)
    );
