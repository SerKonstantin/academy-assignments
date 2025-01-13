package org.academy.messages;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.academy.model.LastNotificatedOrder;
import org.academy.model.Order;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class NotificationsConsumer {

    private final ObjectMapper om;
    private final LastNotificatedOrder lastNotificatedOrder;

    @KafkaListener(topics = "sent_orders", groupId = "payment-group")
    public void consumeSentOrder(String message) {
        try {
            Order order = om.readValue(message, Order.class);
            order.setStatus("NOTIFICATION_SENT");
            // TODO add logger emulating email sent
            lastNotificatedOrder.setOrder(order);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
