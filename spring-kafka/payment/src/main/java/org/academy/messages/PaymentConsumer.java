package org.academy.messages;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.academy.model.LastPayedOrder;
import org.academy.model.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PaymentConsumer {

    private static final Logger LOGGER = LogManager.getLogger(PaymentConsumer.class);

    private final ObjectMapper om;
    private final PaymentProducer producer;
    private final LastPayedOrder lastPayedOrder;

    @KafkaListener(topics = "new_orders", groupId = "payment-group")
    public void consumeNewOrder(String message) {
        try {
            LOGGER.info("PERSONAL | Received message from topic 'new_orders': {}", message);
            Order order = om.readValue(message, Order.class);
            order.setStatus("PAID");
            LOGGER.info("PERSONAL | Order marked as PAID and stored as lastPayedOrder.");
            lastPayedOrder.setOrder(order);
            producer.sendPayment(order);
        } catch (JsonProcessingException e) {
            LOGGER.error("PERSONAL | Error deserializing message: {}", message, e);
            throw new RuntimeException(e);
        }
    }
}
