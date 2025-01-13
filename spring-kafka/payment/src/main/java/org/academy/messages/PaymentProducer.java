package org.academy.messages;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.academy.model.Order;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PaymentProducer {

    private static final Logger LOGGER = LogManager.getLogger(PaymentProducer.class);

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper om;
    private final NewTopic payedOrdersTopic;

    public void sendPayment(Order order) {
        try {
            String message = om.writeValueAsString(order);
            String key = order.getCustomer(); // Используется CustomPartitioner для равномерного распределения по партициям
            String topicName = payedOrdersTopic.name();
            LOGGER.info("Sending message to topic '{}', key '{}': {}", topicName, key, message);
            kafkaTemplate.send(topicName, key, message);
        } catch (JsonProcessingException e) {
            LOGGER.error("PERSONAL | Error serializing order: {}", order, e);
            throw new RuntimeException(e);
        }
    }
}
