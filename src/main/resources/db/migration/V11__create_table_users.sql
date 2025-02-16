create table users(
    id uuid not null primary key,
    name varchar(200) not null,
    email varchar(200) not null unique,
    password varchar(200) not null,
    active boolean not null,
    created_at timestamp,
    updated_at timestamp
);