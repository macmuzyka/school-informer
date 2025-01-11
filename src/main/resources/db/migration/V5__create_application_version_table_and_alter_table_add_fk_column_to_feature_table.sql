CREATE TABLE IF NOT EXISTS application_version
(
    id BIGSERIAL            PRIMARY KEY UNIQUE NOT NULL,
    application_version     VARCHAR(50) UNIQUE NOT NULL,
    description             TEXT,
    created_at              TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_updated_at         TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE IF EXISTS feature
    ADD COLUMN application_version_id BIGINT;

ALTER TABLE IF EXISTS feature
    ADD CONSTRAINT fk_application_version
    FOREIGN KEY (application_version_id)
    REFERENCES application_version (id);

