package com.exemplo.embedding.repository;

import java.util.UUID;

import com.exemplo.embedding.controller.dto.DocumentInsertRequest;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface VectorDocumentRepository extends CrudRepository<DocumentInsertRequest, UUID> {

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

}
