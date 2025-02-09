CREATE TABLE IF NOT EXISTS bookings (
                                        id SERIAL PRIMARY KEY,
                                        user_id INTEGER NOT NULL,
                                        class_id INTEGER NOT NULL,
                                        booking_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                        CONSTRAINT fk_booking_user FOREIGN KEY (user_id)
                                        REFERENCES users(id),
                                        CONSTRAINT fk_booking_class FOREIGN KEY (class_id)
                                        REFERENCES classes(id),
                                        CONSTRAINT unique_booking UNIQUE (user_id, class_id)
    );
