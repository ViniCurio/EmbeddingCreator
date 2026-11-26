CREATE EXTENSION IF NOT EXISTS vector;

CREATE TABLE IF NOT EXISTS embedded_documents (
    id UUID PRIMARY KEY,
    content TEXT NOT NULL,
    metadata JSONB NOT NULL DEFAULT '{}'::jsonb,
    embedding VECTOR(1536) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS embedded_documents_embedding_hnsw_idx
    ON embedded_documents
    USING hnsw (embedding vector_cosine_ops)
    WITH (
        m = 16,
        ef_construction = 64
    );
