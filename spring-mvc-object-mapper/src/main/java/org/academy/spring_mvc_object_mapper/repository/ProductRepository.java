package org.academy.spring_mvc_object_mapper.repository;

import org.academy.spring_mvc_object_mapper.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    Optional<Product> findByName(String name);
}