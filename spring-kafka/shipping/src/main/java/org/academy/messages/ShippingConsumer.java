package org.academy.messages;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.academy.model.LastShippedOrder;
import org.academy.model.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ShippingConsumer {

    private static final Logger LOGGER = LogManager.getLogger(ShippingConsumer.class);

    private final ObjectMapper om;
    private final ShippingProducer producer;
    private final LastShippedOrder lastShippedOrder;

    @KafkaListener(topics = "payed_orders", groupId = "payment-group")
    public void consumePayedOrder(String message) {
        try {
            LOGGER.info("PERSONAL | Received message from topic 'payed_orders': {}", message);
            Order order = om.readValue(message, Order.class);
            order.setStatus("SHIPPED");
            LOGGER.info("PERSONAL | Order marked as SHIPPED and stored as lastShippedOrder.");
            lastShippedOrder.setOrder(order);
            producer.sendPayment(order);
        } catch (JsonProcessingException e) {
            LOGGER.error("PERSONAL | Error deserializing message: {}", message, e);
            throw new RuntimeException(e);
        }
    }

}
