CREATE TABLE IF NOT EXISTS waitlists (
                                            id SERIAL PRIMARY KEY,
                                            user_id INTEGER NOT NULL,
                                            class_id INTEGER NOT NULL,
                                            waitlist_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                            CONSTRAINT fk_waitlist_user FOREIGN KEY (user_id) REFERENCES users(id),
                                            CONSTRAINT fk_waitlist_class
                                            FOREIGN KEY (class_id)
                                            REFERENCES classes(id),
                                            CONSTRAINT unique_waitlist UNIQUE (user_id, class_id)

    );
