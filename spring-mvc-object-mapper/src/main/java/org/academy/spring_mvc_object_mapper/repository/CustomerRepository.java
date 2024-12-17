package org.academy.spring_mvc_object_mapper.repository;

import org.academy.spring_mvc_object_mapper.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
}