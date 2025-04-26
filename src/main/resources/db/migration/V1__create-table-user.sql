CREATE TABLE users(
    id SERIAL PRIMARY KEY, -- Serial -> auto increment
    username TEXT NOT NULL,
    password TEXT NOT NULL,
    role VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
