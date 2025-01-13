package org.academy.controller;

import lombok.AllArgsConstructor;
import org.academy.model.LastNotificatedOrder;
import org.academy.model.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@AllArgsConstructor
public class NotificationsController {

    private static final Logger LOGGER = LogManager.getLogger(NotificationsController.class);

    private final LastNotificatedOrder lastNotificatedOrder;

    @GetMapping
    public String getPageStub() {
        LOGGER.info("PERSONAL | Accessed root page of 'notifications' service.");
        return "Notifications page stub";
    }

    @GetMapping("/last")
    public String getLastNotifications() {
        Order orderDetails = lastNotificatedOrder.getOrder();

        if (orderDetails == null) {
            LOGGER.info("PERSONAL | Accessed page with last notification: kafka messages not found in topic 'new_orders'");
            return "Notifications not found yet.";
        }

        String message = String.format("Notification was sent for user %s. Order contained %s for %d imaginary coins",
                orderDetails.getCustomer(),
                orderDetails.getProduct(),
                orderDetails.getPrice()
        );

        LOGGER.info("PERSONAL | Accessed page with last notification: " + message);
        return message;
    }

}
