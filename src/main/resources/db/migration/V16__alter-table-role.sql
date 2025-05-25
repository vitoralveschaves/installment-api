alter table role add column created_at timestamp not null default now();
alter table role add column updated_at timestamp not null default now();

update role set created_at = now();
update role set updated_at = now();