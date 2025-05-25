alter table installment add column created_at timestamp not null default now();
alter table installment add column updated_at timestamp not null default now();

update installment set created_at = now();
update installment set updated_at = now();