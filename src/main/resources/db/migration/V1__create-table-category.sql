CREATE TABLE category (
    id BIGSERIAL PRIMARY KEY,
    uuid VARCHAR(255) UNIQUE,
    name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);