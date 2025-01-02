CREATE TABLE IF NOT EXISTS feature
(
    id                  BIGSERIAL PRIMARY KEY               NOT NULL,
    description         TEXT,
    created_at          TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    last_updated_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    development_stage   VARCHAR(30)
);