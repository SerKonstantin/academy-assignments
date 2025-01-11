package org.academy.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.academy.messages.OrderProducer;
import org.academy.model.LastCreatedOrder;
import org.academy.model.Order;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class OrdersController {

    private final LastCreatedOrder lastCreatedOrder;
    private final OrderProducer producer;


    @GetMapping
    public String getPageStub() {
        return "Orders page stub";
    }

    @GetMapping("/last")
    public Order getLastCreatedOrder() {
        return lastCreatedOrder.getOrder();
    }

    @PostMapping
    public Order createOrder(@RequestBody @Valid Order order) {
        order.setId(UUID.randomUUID());
        order.setStatus("NEW");
        lastCreatedOrder.setOrder(order);
        producer.sendOrder(order);
        return order;
    }

}
