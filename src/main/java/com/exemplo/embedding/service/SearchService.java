package com.exemplo.embedding.service;

import com.exemplo.embedding.controller.dto.SearchRequest;
import com.exemplo.embedding.controller.dto.SearchResponse;

import java.util.List;
import java.util.Map;

import com.exemplo.embedding.repository.VectorSearchRepository;
import org.springframework.stereotype.Service;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.json.JsonMapper;

@Service
public class SearchService {

    private static final int TOP_K = 3;
    private static final double MIN_SIMILARITY = 0.50;

    private final EmbeddingGenerator embeddingGenerator;
    private final VectorSearchRepository repository;
    private final VectorLiteralConverter vectorLiteralConverter;
    private final JsonMapper jsonMapper;

    public SearchService(
            EmbeddingGenerator embeddingGenerator,
            VectorSearchRepository repository,
            VectorLiteralConverter vectorLiteralConverter,
            JsonMapper jsonMapper
    ) {
        this.embeddingGenerator = embeddingGenerator;
        this.repository = repository;
        this.vectorLiteralConverter = vectorLiteralConverter;
        this.jsonMapper = jsonMapper;
    }

    public List<SearchResponse> search(SearchRequest request) {

        float[] queryEmbedding = embeddingGenerator.generateForQuery(request.query());

        List<SearchResponse> results = repository.findNearest(
                        vectorLiteralConverter.convert(queryEmbedding),
                        MIN_SIMILARITY,
                        TOP_K
                )
                .stream()
                .map(result -> new SearchResponse(
                        result.id(),
                        result.content(),
                        readMetadata(result.metadata()),
                        result.similarity()
                ))
                .toList();

        return results;
    }

    private Map<String, Object> readMetadata(String metadata) {
        try {
            return jsonMapper.readValue(metadata, new TypeReference<>() {
            });
        }
        catch (Exception exception) {
            throw new IllegalStateException("Metadados inválidos no banco", exception);
        }
    }
}
