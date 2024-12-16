package org.academy.spring_mvc_with_jsonview.repository;

import org.academy.spring_mvc_with_jsonview.model.Good;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GoodRepository extends JpaRepository<Good, UUID> {
}