alter table category add column created_at timestamp not null default now();
alter table category add column updated_at timestamp not null default now();

update category set created_at = now();
update category set updated_at = now();