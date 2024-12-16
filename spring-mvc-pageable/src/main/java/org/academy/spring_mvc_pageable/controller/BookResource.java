package org.academy.spring_mvc_pageable.controller;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.academy.spring_mvc_pageable.service.BookService;
import org.academy.spring_mvc_pageable.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookResource {

    private final BookService bookService;

    @GetMapping
    public PagedModel<Book> getAll(Pageable pageable) {
        Page<Book> books = bookService.getAll(pageable);
        return new PagedModel<>(books);
    }

    @GetMapping("/{id}")
    public Book getOne(@PathVariable UUID id) {
        return bookService.getOne(id);
    }

    @GetMapping("/by-ids")
    public List<Book> getMany(@RequestParam List<UUID> ids) {
        return bookService.getMany(ids);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book create(@RequestBody @Valid Book book) {
        return bookService.create(book);
    }

    @PatchMapping("/{id}")
    public Book patch(@PathVariable UUID id, @RequestBody JsonNode patchNode) throws IOException {
        return bookService.patch(id, patchNode);
    }

    @PatchMapping
    public List<UUID> patchMany(@RequestParam @Valid List<UUID> ids, @RequestBody JsonNode patchNode) throws IOException {
        return bookService.patchMany(ids, patchNode);
    }

    @DeleteMapping("/{id}")
    public Book delete(@PathVariable UUID id) {
        return bookService.delete(id);
    }

    @DeleteMapping
    public void deleteMany(@RequestParam List<UUID> ids) {
        bookService.deleteMany(ids);
    }
}
