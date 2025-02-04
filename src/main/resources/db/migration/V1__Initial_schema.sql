-- V1__Initial_schema.sql

-- Membership Type table
CREATE TABLE IF NOT EXISTS membership_type (
                                               id SERIAL PRIMARY KEY,
                                               name VARCHAR(255) NOT NULL,
                                               description TEXT,
                                               price NUMERIC(10,2) NOT NULL
);

-- Users table: members, trainers, admins
CREATE TABLE IF NOT EXISTS users (
                                     id SERIAL PRIMARY KEY,
                                     name VARCHAR(255) NOT NULL,
                                     email VARCHAR(255) UNIQUE NOT NULL,
                                     role VARCHAR(50) NOT NULL CHECK (role IN ('MEMBER', 'TRAINER', 'ADMIN')),
                                     membership_type_id INTEGER,
                                     CONSTRAINT fk_membership_type FOREIGN KEY (membership_type_id)
                                         REFERENCES membership_type(id)
);

-- Classes table
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

-- Bookings table (many-to-many between members and classes)
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
