package com.exemplo.embedding.api;

import java.util.Map;
import java.util.UUID;

public record SearchResultResponse(
        UUID id,
        String content,
        Map<String, Object> metadata,
        double similarity
) {
}
