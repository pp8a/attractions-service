-- create tables
DROP TABLE IF EXISTS city CASCADE;
DROP TABLE IF EXISTS attraction CASCADE;
DROP TABLE IF EXISTS service CASCADE;
DROP TABLE IF EXISTS attraction_service CASCADE;

CREATE TABLE IF NOT EXISTS city (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(255) NOT NULL,
    population BIGINT,
    has_metro BOOLEAN
);

CREATE TABLE IF NOT EXISTS attraction (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(255) NOT NULL,
    created_date DATE,
    description TEXT,
    type VARCHAR(255),
    city_id BIGINT,
    CONSTRAINT fk_attraction_city FOREIGN KEY (city_id) REFERENCES city (id)
);

CREATE TABLE IF NOT EXISTS service (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(255) NOT NULL,
    description TEXT
);

CREATE TABLE IF NOT EXISTS attraction_service (
    attraction_id BIGINT,
    service_id BIGINT,
    CONSTRAINT fk_attraction_service_attraction FOREIGN KEY (attraction_id) REFERENCES attraction (id),
    CONSTRAINT fk_attraction_service_service FOREIGN KEY (service_id) REFERENCES service (id),
    CONSTRAINT pk_attraction_service PRIMARY KEY (attraction_id, service_id)
);