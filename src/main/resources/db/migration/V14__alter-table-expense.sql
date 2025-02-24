alter table expense add column user_id uuid;
alter table expense add constraint user_expense_id_fk foreign key (user_id) references users (id);

alter table revenue add column user_id uuid;
alter table revenue add constraint user_revenue_id_fk foreign key (user_id) references users (id);