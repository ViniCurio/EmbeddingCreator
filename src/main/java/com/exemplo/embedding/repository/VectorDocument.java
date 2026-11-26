package com.exemplo.embedding.repository;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("embedded_documents")
public record VectorDocument(@Id UUID id) {
}
