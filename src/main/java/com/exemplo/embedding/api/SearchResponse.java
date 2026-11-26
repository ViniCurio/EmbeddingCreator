package com.exemplo.embedding.api;

import java.util.List;

public record SearchResponse(
        String query,
        List<SearchResultResponse> results
) {
}
