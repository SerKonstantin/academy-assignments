package org.academy.spring_mvc_pageable.repository;

import org.academy.spring_mvc_pageable.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {
    List<Book> findByAuthorId(UUID authorId);
}