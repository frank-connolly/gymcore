CREATE TABLE IF NOT EXISTS classes (
                                        id SERIAL PRIMARY KEY,
                                        title VARCHAR(255) NOT NULL,
                                        description TEXT,
                                        scheduled_at TIMESTAMP NOT NULL,
                                        capacity INTEGER NOT NULL,
                                        trainer_id INTEGER NOT NULL,
                                        CONSTRAINT fk_trainer FOREIGN KEY (trainer_id)
                                        REFERENCES users(id)
    );
