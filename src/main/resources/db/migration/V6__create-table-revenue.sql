CREATE TABLE revenue (
    id BIGSERIAL PRIMARY KEY,
    uuid VARCHAR(255) UNIQUE,
    title VARCHAR(200) NOT NULL,
    total_value NUMERIC(19,2) NOT NULL,
    category_id BIGINT REFERENCES category(id),
    initial_date DATE NOT NULL,
    type VARCHAR(255),
    user_id BIGINT REFERENCES users(id),
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);