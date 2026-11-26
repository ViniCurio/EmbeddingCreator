package com.exemplo.embedding.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface VectorDocumentRepository extends CrudRepository<VectorDocument, UUID> {

    @Modifying
    @Transactional
    @Query("""
            INSERT INTO embedded_documents (id, content, metadata, embedding)
            VALUES (:id, :content, CAST(:metadata AS jsonb), CAST(:embedding AS vector))
            """)
    void insert(
            @Param("id") UUID id,
            @Param("content") String content,
            @Param("metadata") String metadata,
            @Param("embedding") String embedding
    );

    @Transactional(readOnly = true)
    @Query("""
                SELECT
                    id,
                    content,
                    metadata::text AS metadata,
                    1 - (embedding <=> CAST(:embedding AS vector)) AS similarity
                FROM embedded_documents
                WHERE 1 - (embedding <=> CAST(:embedding AS vector)) >= :minimumSimilarity
                ORDER BY embedding <=> CAST(:embedding AS vector)
                LIMIT :topK
                """)
    List<VectorSearchResult> findNearest(
            @Param("embedding") String embedding,
            @Param("minimumSimilarity") double minimumSimilarity,
            @Param("topK") int topK
    );
}
