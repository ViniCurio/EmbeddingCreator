package com.exemplo.embedding.service;

import com.exemplo.embedding.api.SearchRequest;
import com.exemplo.embedding.api.SearchResponse;
import com.exemplo.embedding.api.SearchResultResponse;
import com.exemplo.embedding.repository.VectorDocumentRepository;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.json.JsonMapper;

@Service
public class SemanticSearchService {

    private static final int TOP_K = 5;
    private static final double MIN_SIMILARITY = 0.70;

    private final EmbeddingGenerator embeddingGenerator;
    private final VectorDocumentRepository repository;
    private final VectorLiteralConverter vectorLiteralConverter;
    private final JsonMapper jsonMapper;

    public SemanticSearchService(
            EmbeddingGenerator embeddingGenerator,
            VectorDocumentRepository repository,
            VectorLiteralConverter vectorLiteralConverter,
            JsonMapper jsonMapper
    ) {
        this.embeddingGenerator = embeddingGenerator;
        this.repository = repository;
        this.vectorLiteralConverter = vectorLiteralConverter;
        this.jsonMapper = jsonMapper;
    }

    public SearchResponse search(SearchRequest request) {

        float[] queryEmbedding = embeddingGenerator.generateForQuery(request.query());

        List<SearchResultResponse> results = repository.findNearest(
                        vectorLiteralConverter.convert(queryEmbedding),
                        MIN_SIMILARITY,
                        TOP_K
                )
                .stream()
                .map(result -> new SearchResultResponse(
                        result.id(),
                        result.content(),
                        readMetadata(result.metadata()),
                        result.similarity()
                ))
                .toList();

        return new SearchResponse(request.query(), results);
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
