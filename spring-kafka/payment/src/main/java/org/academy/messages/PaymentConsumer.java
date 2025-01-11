package org.academy.messages;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.academy.model.LastPayedOrder;
import org.academy.model.Order;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PaymentConsumer {

    private final ObjectMapper om;
    private final PaymentProducer producer;
    private final LastPayedOrder lastPayedOrder;

    @KafkaListener(topics = "new_orders", groupId = "payment-group") // TODO check group
    public void consumeNewOrder(String message) {
        try {
            Order order = om.readValue(message, Order.class);
            order.setStatus("PAID");
            lastPayedOrder.setOrder(order);
            producer.sendPayment(order);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
