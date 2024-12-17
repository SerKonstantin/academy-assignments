package org.academy.spring_mvc_object_mapper.repository;

import org.academy.spring_mvc_object_mapper.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
}