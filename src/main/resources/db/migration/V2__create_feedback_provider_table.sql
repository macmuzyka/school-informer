CREATE TABLE IF NOT EXISTS feedback_provider
(
    id                     BIGSERIAL PRIMARY KEY,
    feedback_provider_name VARCHAR(255),
    provider_email         VARCHAR(255) UNIQUE,
    created_at             TIMESTAMP DEFAULT CURRENT_TIMESTAMP
)