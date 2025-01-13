package org.academy.messages;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.academy.model.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderProducer {

    private static final String NEW_ORDERS_TOPIC = "new_orders";

    private static final Logger LOGGER = LogManager.getLogger(OrderProducer.class);

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper om;

    public void sendOrder(Order order) {
        try {
            String message = om.writeValueAsString(order);
            LOGGER.info("PERSONAL | Sending message to topic '{}': {}", NEW_ORDERS_TOPIC, message);
            kafkaTemplate.send(NEW_ORDERS_TOPIC, message);
        } catch (JsonProcessingException e) {
            LOGGER.error("PERSONAL | Error serializing order: {}", order, e);
            throw new RuntimeException(e);
        }
    }

}
