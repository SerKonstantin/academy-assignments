package org.academy.spring_mvc_pageable.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.academy.spring_mvc_pageable.model.Author;
import org.academy.spring_mvc_pageable.model.Book;
import org.academy.spring_mvc_pageable.repository.AuthorRepository;
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
public class AuthorService{

    private final AuthorRepository authorRepository;

    private final ObjectMapper objectMapper;

    private final BookRepository bookRepository;

    public Page<Author> getAll(Pageable pageable) {
        return authorRepository.findAll(pageable);
    }

    public Author getOne(UUID id) {
        Optional<Author> authorOptional = authorRepository.findById(id);
        return authorOptional.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));
    }

    public List<Author> getMany(List<UUID> ids) {
        return authorRepository.findAllById(ids);
    }

    public Author create(Author author) {
        return authorRepository.save(author);
    }

    public Author patch(UUID id, JsonNode patchNode) throws IOException {
        Author author = authorRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));

        objectMapper.readerForUpdating(author).readValue(patchNode);

        return authorRepository.save(author);
    }

    public List<UUID> patchMany(List<UUID> ids, JsonNode patchNode) throws IOException {
        Collection<Author> authors = authorRepository.findAllById(ids);

        for (Author author : authors) {
            objectMapper.readerForUpdating(author).readValue(patchNode);
        }

        List<Author> resultAuthors = authorRepository.saveAll(authors);
        return resultAuthors.stream()
                .map(Author::getId)
                .toList();
    }

    public Author delete(UUID id) {
        Author author = authorRepository.findById(id).orElse(null);
        if (author != null) {
            List<Book> books = bookRepository.findByAuthorId(author.getId());
            bookRepository.deleteAllById(books.stream().map(Book::getId).toList());
            authorRepository.delete(author);
        }
        return author;
    }

    public void deleteMany(List<UUID> ids) {
        authorRepository.deleteAllById(ids);
    }
}
