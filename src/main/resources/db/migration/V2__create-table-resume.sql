CREATE TABLE resume(
    id SERIAL PRIMARY KEY, -- Serial -> auto increment
    filePath TEXT NOT NULL,
    position VARCHAR(50) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    user_id INTEGER NOT NULL,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
