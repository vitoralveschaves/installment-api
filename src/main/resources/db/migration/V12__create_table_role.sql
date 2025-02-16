create table role(
    id uuid not null primary key,
    name varchar(50) not null unique
);