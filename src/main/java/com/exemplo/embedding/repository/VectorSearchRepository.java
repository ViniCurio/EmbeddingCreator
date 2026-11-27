package com.exemplo.embedding.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public interface VectorSearchRepository extends CrudRepository<VectorSearchResult, UUID> {

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
