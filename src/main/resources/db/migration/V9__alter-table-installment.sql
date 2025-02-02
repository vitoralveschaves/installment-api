alter table installment add constraint installment_expense_id_fkey foreign key (expense_id) references expense (id);
alter table installment drop column user_id;
alter table expense drop column user_id;