package org.academy.spring_mvc_with_jsonview.repository;

import org.academy.spring_mvc_with_jsonview.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
}