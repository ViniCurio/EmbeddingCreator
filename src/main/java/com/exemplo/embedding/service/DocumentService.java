package com.exemplo.embedding.service;

import com.exemplo.embedding.api.DocumentInsertRequest;
import com.exemplo.embedding.api.DocumentInsertResponse;
import com.exemplo.embedding.repository.VectorDocumentRepository;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Service;
import tools.jackson.databind.json.JsonMapper;

@Service
public class DocumentService {

    private final EmbeddingGenerator embeddingGenerator;
    private final VectorDocumentRepository repository;
    private final VectorLiteralConverter vectorLiteralConverter;
    private final JsonMapper jsonMapper;

    public DocumentService(
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

    public DocumentInsertResponse insert(DocumentInsertRequest request) {
        float[] embedding = embeddingGenerator.generateForDocument(request.content());

        UUID id = UUID.randomUUID();

        try {
            String metadata = jsonMapper.writeValueAsString(
                    request.metadata() == null ? Map.of() : request.metadata()
            );

            repository.insert(
                    id,
                    request.content(),
                    metadata,
                    vectorLiteralConverter.convert(embedding)
            );
        }
        catch (Exception exception) {
            throw new IllegalStateException("Não foi possível salvar o documento vetorial", exception);
        }

        return new DocumentInsertResponse(id);
    }
}
