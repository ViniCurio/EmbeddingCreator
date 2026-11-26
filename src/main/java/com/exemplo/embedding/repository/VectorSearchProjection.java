package com.exemplo.embedding.repository;

import java.util.UUID;

public interface VectorSearchProjection {

    UUID getId();

    String getContent();

    String getMetadata();

    double getSimilarity();
}
