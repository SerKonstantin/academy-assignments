package org.academy.messages;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.academy.model.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PaymentProducer {

    private static final String PAYED_ORDERS_TOPIC = "payed_orders";

    private static final Logger LOGGER = LogManager.getLogger(PaymentProducer.class);

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper om;

    public void sendPayment(Order order) {
        try {
            String message = om.writeValueAsString(order);
            LOGGER.info("PERSONAL | Sending message to topic '{}': {}", PAYED_ORDERS_TOPIC, message);
            kafkaTemplate.send(PAYED_ORDERS_TOPIC, message);
        } catch (JsonProcessingException e) {
            LOGGER.error("PERSONAL | Error serializing order: {}", order, e);
            throw new RuntimeException(e);
        }
    }
}
