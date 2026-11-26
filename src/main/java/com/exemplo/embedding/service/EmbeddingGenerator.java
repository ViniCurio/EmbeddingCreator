package com.exemplo.embedding.service;

import java.util.List;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.ai.google.genai.text.GoogleGenAiTextEmbeddingOptions;
import org.springframework.ai.google.genai.text.GoogleGenAiTextEmbeddingOptions.TaskType;
import org.springframework.stereotype.Service;

@Service
public class EmbeddingGenerator {

    private static final String MODEL = "gemini-embedding-001";
    private static final int DIMENSIONS = 1536;

    private final EmbeddingModel embeddingModel;

    public EmbeddingGenerator(EmbeddingModel embeddingModel) {
        this.embeddingModel = embeddingModel;
    }

    public float[] generateForDocument(String content) {
        return generate(content, TaskType.RETRIEVAL_DOCUMENT);
    }

    public float[] generateForQuery(String query) {
        return generate(query, TaskType.RETRIEVAL_QUERY);
    }

    private float[] generate(String text, TaskType taskType) {
        var options = GoogleGenAiTextEmbeddingOptions.builder()
                .model(MODEL)
                .dimensions(DIMENSIONS)
                .taskType(taskType)
                .build();

        var request = new EmbeddingRequest(List.of(text), options);

        return embeddingModel.call(request)
                .getResults()
                .getFirst()
                .getOutput();
    }
}
