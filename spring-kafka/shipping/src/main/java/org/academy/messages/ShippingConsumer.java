package org.academy.messages;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.academy.model.LastShippedOrder;
import org.academy.model.Order;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ShippingConsumer {

    private final ObjectMapper om;
    private final ShippingProducer producer;
    private final LastShippedOrder lastShippedOrder;

    @KafkaListener(topics = "payed_orders", groupId = "payment-group")
    public void consumePayedOrder(String message) {
        try {
            Order order = om.readValue(message, Order.class);
            order.setStatus("SHIPPED");
            lastShippedOrder.setOrder(order);
            producer.sendPayment(order);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
