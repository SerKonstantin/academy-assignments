package org.academy.spring_mvc_with_jsonview.repository;

import org.academy.spring_mvc_with_jsonview.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);
}