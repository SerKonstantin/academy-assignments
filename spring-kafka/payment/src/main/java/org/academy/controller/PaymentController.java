package org.academy.controller;

import lombok.AllArgsConstructor;
import org.academy.model.LastPayedOrder;
import org.academy.model.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@AllArgsConstructor
public class PaymentController {

    private static final Logger LOGGER = LogManager.getLogger(PaymentController.class);

    private final LastPayedOrder lastPayedOrder;

    @GetMapping
    public String getPageStub() {
        LOGGER.info("PERSONAL | Accessed root page of 'payment' service.");
        return "Payment page stub.";
    }

    @GetMapping("/last")
    public Order getLastPayedOrder() {
        LOGGER.info("PERSONAL | Accessed page with paid order: {}", lastPayedOrder.getOrder());
        return lastPayedOrder.getOrder();
    }
}
