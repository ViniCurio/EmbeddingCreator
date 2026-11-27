package com.exemplo.embedding.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record SearchRequest(
        @NotBlank(message = "query é obrigatória")
        String query
) {
}
