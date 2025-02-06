CREATE TABLE IF NOT EXISTS users (
                                        id SERIAL PRIMARY KEY,
                                        name VARCHAR(255) NOT NULL,
                                        email VARCHAR(255) UNIQUE NOT NULL,
                                        role VARCHAR(50) NOT NULL CHECK (role IN ('MEMBER', 'TRAINER', 'ADMIN')),
                                        membership_type_id INTEGER,
                                        CONSTRAINT fk_membership_type FOREIGN KEY (membership_type_id)
                                        REFERENCES membership_type(id)
    );
