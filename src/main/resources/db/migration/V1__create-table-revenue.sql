create table revenue(
    id uuid not null primary key,
    title varchar(200) not null,
    total_value numeric(18, 2) not null,
    is_installment boolean not null,
    quantity_installments int,
    initial_date date not null,
    created_at timestamp,
    updated_at timestamp,
    user_id uuid
);