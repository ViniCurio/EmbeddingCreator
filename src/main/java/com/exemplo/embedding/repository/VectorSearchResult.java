package com.exemplo.embedding.repository;

import java.util.UUID;

public record VectorSearchResult(
        UUID id,
        String content,
        String metadata,
        double similarity
) {
}
