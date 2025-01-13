package org.academy.messages;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.academy.model.LastNotificatedOrder;
import org.academy.model.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class NotificationsConsumer {

    private static final Logger LOGGER = LogManager.getLogger(NotificationsConsumer.class);

    private final ObjectMapper om;
    private final LastNotificatedOrder lastNotificatedOrder;

    @KafkaListener(topics = "sent_orders", groupId = "payment-group")
    public void consumeSentOrder(String message) {
        try {
            LOGGER.info("PERSONAL | Received message from topic 'sent_orders': {}", message);
            Order order = om.readValue(message, Order.class);
            order.setStatus("NOTIFICATION_SENT");
            // Email is hypothetically sent to customer
            LOGGER.info("PERSONAL | Order marked as NOTIFICATION_SENT and stored as lastNotificatedOrder.");
            lastNotificatedOrder.setOrder(order);
        } catch (JsonProcessingException e) {
            LOGGER.error("PERSONAL | Error deserializing message: {}", message, e);
            throw new RuntimeException(e);
        }
    }
}
