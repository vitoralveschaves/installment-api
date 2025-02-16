create table user_role(
    id uuid not null primary key,
    user_id uuid not null references users(id) on delete cascade,
    role_id uuid not null references role(id) on delete cascade,
    unique (user_id, role_id)
);