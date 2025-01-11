package org.academy.messages;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.academy.model.Order;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderProducer {

    private static final String NEW_ORDERS_TOPIC = "new_orders";

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final ObjectMapper om;

    public void sendOrder(Order order) {
        try {
            String message = om.writeValueAsString(order);
            kafkaTemplate.send(NEW_ORDERS_TOPIC, message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
