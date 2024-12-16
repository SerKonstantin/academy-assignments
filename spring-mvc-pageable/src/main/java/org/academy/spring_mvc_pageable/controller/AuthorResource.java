package org.academy.spring_mvc_pageable.controller;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.academy.spring_mvc_pageable.model.Author;
import org.academy.spring_mvc_pageable.service.AuthorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor
public class AuthorResource {

    private final AuthorService authorService;

    @GetMapping
    public PagedModel<Author> getAll(Pageable pageable) {
        Page<Author> authors = authorService.getAll(pageable);
        return new PagedModel<>(authors);
    }

    @GetMapping("/{id}")
    public Author getOne(@PathVariable UUID id) {
        return authorService.getOne(id);
    }

    @GetMapping("/by-ids")
    public List<Author> getMany(@RequestParam List<UUID> ids) {
        return authorService.getMany(ids);
    }

    @PostMapping
    public Author create(@RequestBody @Valid Author author) {
        return authorService.create(author);
    }

    @PatchMapping("/{id}")
    public Author patch(@PathVariable UUID id, @RequestBody JsonNode patchNode) throws IOException {
        return authorService.patch(id, patchNode);
    }

    @PatchMapping
    public List<UUID> patchMany(@RequestParam @Valid List<UUID> ids, @RequestBody JsonNode patchNode) throws IOException {
        return authorService.patchMany(ids, patchNode);
    }

    @DeleteMapping("/{id}")
    public Author delete(@PathVariable UUID id) {
        return authorService.delete(id);
    }

    @DeleteMapping
    public void deleteMany(@RequestParam List<UUID> ids) {
        authorService.deleteMany(ids);
    }
}
