package org.academy.controller;

import lombok.AllArgsConstructor;
import org.academy.model.LastShippedOrder;
import org.academy.model.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@AllArgsConstructor
public class ShippingController {

    private static final Logger LOGGER = LogManager.getLogger(ShippingController.class);

    private final LastShippedOrder lastShippedOrder;

    @GetMapping
    public String getPageStub() {
        LOGGER.info("PERSONAL | Accessed root page of 'shipping' service.");
        return "Shipping page stub.";
    }

    @GetMapping("/last")
    public Order getLastShippedOrder() {
        LOGGER.info("PERSONAL | Accessed page with shipped order: {}", lastShippedOrder.getOrder());
        return lastShippedOrder.getOrder();
    }

}
