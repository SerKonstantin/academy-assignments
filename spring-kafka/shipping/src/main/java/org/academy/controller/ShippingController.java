package org.academy.controller;

import lombok.AllArgsConstructor;
import org.academy.model.LastShippedOrder;
import org.academy.model.Order;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@AllArgsConstructor
public class ShippingController {

    private final LastShippedOrder lastShippedOrder;

    @GetMapping
    public String getPageStub() {
        return "Shipping page stub.";
    }

    @GetMapping("/last")
    public Order getLastShippedOrder() {
        return lastShippedOrder.getOrder();
    }

}
