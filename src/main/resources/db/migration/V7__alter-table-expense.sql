alter table expense drop column category;

alter table expense add column category_id uuid;
alter table expense add constraint category_expense_id_fk foreign key (category_id) references category (id);