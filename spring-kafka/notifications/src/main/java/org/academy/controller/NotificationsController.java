package org.academy.controller;

import lombok.AllArgsConstructor;
import org.academy.model.LastNotificatedOrder;
import org.academy.model.Order;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@AllArgsConstructor
public class NotificationsController {

    private final LastNotificatedOrder lastNotificatedOrder;

    @GetMapping
    public String getPageStub() {
        return "Notifications page stub";
    }

    @GetMapping("/last")
    public String getLastNotifications() {
        Order orderDetails = lastNotificatedOrder.getOrder();

        if (orderDetails == null) {
            return "Notifications not found yet.";
        }

        return String.format("Notification was sent for user %s. Order contained %s for %d imaginary coins",
                orderDetails.getCustomer(),
                orderDetails.getProduct(),
                orderDetails.getPrice()
        );
    }

}
