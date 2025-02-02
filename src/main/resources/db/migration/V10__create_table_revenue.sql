create TYPE revenue_type AS ENUM ('INCOME', 'INVESTMENT');

create table revenue(
    id uuid not null primary key,
    title varchar(200) not null,
    total_value numeric(18, 2) not null,
    quantity_installments int,
    initial_date date not null,
    category_id uuid not null references category(id),
    type revenue_type,
    created_at timestamp,
    updated_at timestamp
);