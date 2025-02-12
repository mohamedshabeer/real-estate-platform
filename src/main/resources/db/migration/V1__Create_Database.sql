DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_database WHERE datname = 'realestate_db') THEN
        CREATE DATABASE realestate_db;
END IF;
END $$;