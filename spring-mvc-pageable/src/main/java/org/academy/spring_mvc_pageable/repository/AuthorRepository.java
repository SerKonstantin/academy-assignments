package org.academy.spring_mvc_pageable.repository;

import org.academy.spring_mvc_pageable.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AuthorRepository extends JpaRepository<Author, UUID> {
}