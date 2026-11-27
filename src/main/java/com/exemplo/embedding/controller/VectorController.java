package com.exemplo.embedding.controller;

import com.exemplo.embedding.controller.dto.DocumentInsertRequest;
import com.exemplo.embedding.controller.dto.SearchRequest;
import com.exemplo.embedding.controller.dto.SearchResponse;
import com.exemplo.embedding.service.DocumentService;
import com.exemplo.embedding.service.SearchService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class VectorController {

    private final DocumentService documentService;
    private final SearchService searchService;

    public VectorController(DocumentService documentService, SearchService searchService) {
        this.documentService = documentService;
        this.searchService = searchService;
    }

    @PostMapping("/documents")
    @ResponseStatus(HttpStatus.CREATED)
    public void insert(@Valid @RequestBody DocumentInsertRequest request) {
        documentService.insert(request);
    }

    @PostMapping("/search")
    public List<SearchResponse> search(@Valid @RequestBody SearchRequest request) {
        return searchService.search(request);
    }
}
