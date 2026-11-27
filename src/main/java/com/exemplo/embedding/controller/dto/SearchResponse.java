package com.exemplo.embedding.controller.dto;

import java.util.Map;
import java.util.UUID;

public record SearchResponse(
        UUID id,
        String content,
        Map<String, Object> metadata,
        double similarity
) {
}
