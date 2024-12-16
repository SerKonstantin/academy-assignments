package org.academy.spring_mvc_pageable.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.academy.spring_mvc_pageable.model.Book;
import org.academy.spring_mvc_pageable.repository.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;

    private final ObjectMapper objectMapper;

    public Page<Book> getAll(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    public Book getOne(UUID id) {
        Optional<Book> bookOptional = bookRepository.findById(id);
        return bookOptional.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));
    }

    public List<Book> getMany(List<UUID> ids) {
        return bookRepository.findAllById(ids);
    }

    public Book create(Book book) {
        return bookRepository.save(book);
    }

    public Book patch(UUID id, JsonNode patchNode) throws IOException {
        Book book = bookRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));

        objectMapper.readerForUpdating(book).readValue(patchNode);

        return bookRepository.save(book);
    }

    public List<UUID> patchMany(List<UUID> ids, JsonNode patchNode) throws IOException {
        Collection<Book> books = bookRepository.findAllById(ids);

        for (Book book : books) {
            objectMapper.readerForUpdating(book).readValue(patchNode);
        }

        List<Book> resultBooks = bookRepository.saveAll(books);
        return resultBooks.stream()
                .map(Book::getId)
                .toList();
    }

    public Book delete(UUID id) {
        Book book = bookRepository.findById(id).orElse(null);
        if (book != null) {
            bookRepository.delete(book);
        }
        return book;
    }

    public void deleteMany(List<UUID> ids) {
        bookRepository.deleteAllById(ids);
    }
}
