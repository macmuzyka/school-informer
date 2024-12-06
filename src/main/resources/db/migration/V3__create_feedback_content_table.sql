CREATE TABLE IF NOT EXISTS feedback_content
(
    id                   BIGSERIAL PRIMARY KEY               NOT NULL,
    feedback_content     TEXT,
    created_at           TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    feedback_provider_id BIGINT,
    CONSTRAINT fk_feedback_provider
        FOREIGN KEY (feedback_provider_id)
            REFERENCES feedback_provider (id)
            ON DELETE CASCADE

)