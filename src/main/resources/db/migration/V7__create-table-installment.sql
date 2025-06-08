CREATE TABLE installment (
    id BIGSERIAL PRIMARY KEY,
    uuid VARCHAR(255) UNIQUE,
    current_month DATE NOT NULL,
    installment_number INTEGER NOT NULL,
    installment_value NUMERIC(19,2) NOT NULL,
    quantity_installments INTEGER,
    is_paid BOOLEAN NOT NULL,
    initial_date DATE NOT NULL,
    expense_id BIGINT REFERENCES expense(id),
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);