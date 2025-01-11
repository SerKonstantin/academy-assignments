package org.academy.controller;

import lombok.AllArgsConstructor;
import org.academy.model.LastPayedOrder;
import org.academy.model.Order;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@AllArgsConstructor
public class PaymentController {

    private final LastPayedOrder lastPayedOrder;

    @GetMapping
    public String getPageStub() {
        return "Payment page stub.";
    }

    @GetMapping("/last")
    public Order getLastPayedOrder() {
        return lastPayedOrder.getOrder();
    }
}
