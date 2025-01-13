package org.academy.messages;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.academy.model.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.UUID;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@AllArgsConstructor
class OrderProducerTests {

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @InjectMocks
    private OrderProducer producer;

    private final ObjectMapper om;

    @Test
    void testSendOrder() throws Exception {
        Order order = new Order(UUID.randomUUID(), "John Doe", "IPhone", 500, "NEW");
        String expected = om.writeValueAsString(order);

        producer.sendOrder(order);

        verify(kafkaTemplate).send("new_orders", expected);
    }

}