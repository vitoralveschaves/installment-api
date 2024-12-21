create table installment(
    id uuid not null primary key,
    current_month date not null,
    installment_number int not null,
    installment_value numeric(18, 2) not null,
    quantity_installments int,
    is_paid boolean not null,
    initial_date date not null,
    user_id uuid,
    revenue_id uuid not null references revenue(id)
);