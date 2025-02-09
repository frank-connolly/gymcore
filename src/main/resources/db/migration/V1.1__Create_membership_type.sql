CREATE TABLE IF NOT EXISTS membership_type (
                                               id SERIAL PRIMARY KEY,
                                               name VARCHAR(255) NOT NULL,
                                                description TEXT,
                                                price NUMERIC(10,2) NOT NULL
    );
