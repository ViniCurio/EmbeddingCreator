package com.exemplo.embedding.api;

import com.exemplo.embedding.service.DocumentService;
import com.exemplo.embedding.service.SemanticSearchService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class VectorController {

    private final DocumentService documentService;
    private final SemanticSearchService semanticSearchService;

    public VectorController(DocumentService documentService, SemanticSearchService semanticSearchService) {
        this.documentService = documentService;
        this.semanticSearchService = semanticSearchService;
    }

    @PostMapping("/documents")
    @ResponseStatus(HttpStatus.CREATED)
    public DocumentInsertResponse insert(@Valid @RequestBody DocumentInsertRequest request) {
        return documentService.insert(request);
    }

    @PostMapping("/search")
    public SearchResponse search(@Valid @RequestBody SearchRequest request) {
        return semanticSearchService.search(request);
    }
}
