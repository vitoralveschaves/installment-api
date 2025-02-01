alter table installment drop constraint installment_revenue_id_fkey;
alter table revenue rename to expense;
alter table installment rename column revenue_id to expense_id;
alter table installment add constraint installment_expense_id_fkey foreign key (expense_id) references expense (id) on delete cascade;