alter table user_role add column created_at timestamp not null default now();
alter table user_role add column updated_at timestamp not null default now();

update user_role set created_at = now();
update user_role set updated_at = now();