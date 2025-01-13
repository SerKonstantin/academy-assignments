package org.academy.messages;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.academy.model.Order;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ShippingProducer {

    private static final String SENT_ORDERS_TOPIC = "sent_orders";

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper om;

    public void sendPayment(Order order) {
        try {
            String message = om.writeValueAsString(order);
            kafkaTemplate.send(SENT_ORDERS_TOPIC, message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
