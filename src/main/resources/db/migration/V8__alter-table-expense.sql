alter table expense drop constraint revenue_pkey cascade;
alter table expense add constraint expense_pkey primary key (id)