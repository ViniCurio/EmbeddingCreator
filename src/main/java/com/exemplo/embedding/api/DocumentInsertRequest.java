package com.exemplo.embedding.api;

import jakarta.validation.constraints.NotBlank;

import java.util.Map;

public record DocumentInsertRequest(
        @NotBlank(message = "content é obrigatório")
        String content,
        Map<String, Object> metadata
) {
}
