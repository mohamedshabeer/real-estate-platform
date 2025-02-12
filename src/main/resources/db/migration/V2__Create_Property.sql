CREATE TABLE IF NOT EXISTS property (
                          id SERIAL PRIMARY KEY,
                          title VARCHAR(255) NOT NULL,
                          description TEXT,
                          location VARCHAR(255),
                          price DECIMAL(10,2) NOT NULL,
                          is_available BOOLEAN DEFAULT TRUE,
                          views INT DEFAULT 0,
                          discount DECIMAL(10,2) DEFAULT 0
);
