package org.academy.spring_mvc_object_mapper.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.academy.spring_mvc_object_mapper.dto.OrderDto;
import org.academy.spring_mvc_object_mapper.mapper.OrderMapper;
import org.academy.spring_mvc_object_mapper.model.Customer;
import org.academy.spring_mvc_object_mapper.model.Order;
import org.academy.spring_mvc_object_mapper.repository.CustomerRepository;
import org.academy.spring_mvc_object_mapper.repository.OrderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OrderService{

    private final OrderMapper orderMapper;

    private final OrderRepository orderRepository;

    private final ObjectMapper objectMapper;

    private final CustomerRepository customerRepository;

    public List<OrderDto> getAll() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(orderMapper::toOrderDto)
                .toList();
    }

    public OrderDto getOne(UUID id) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        return orderMapper.toOrderDto(orderOptional.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id))));
    }

    public OrderDto create(OrderDto dto) {
        Customer customer = customerRepository.findById(dto.getCustomerId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));

        Order order = orderMapper.toEntity(dto);
        order.setCustomer(customer);

        Order resultOrder = orderRepository.save(order);
        return orderMapper.toOrderDto(resultOrder);
    }

    public OrderDto patch(UUID id, JsonNode patchNode) throws IOException {
        Order order = orderRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));

        OrderDto orderDto = orderMapper.toOrderDto(order);
        objectMapper.readerForUpdating(orderDto).readValue(patchNode);

        if (orderDto.getCustomerId() != null) {
            Customer customer = customerRepository.findById(orderDto.getCustomerId()).orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
            order.setCustomer(customer);
        }

        orderMapper.updateWithNull(orderDto, order);
        Order resultOrder = orderRepository.save(order);
        return orderMapper.toOrderDto(resultOrder);
    }

    public OrderDto delete(UUID id) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order != null) {
            orderRepository.delete(order);
        }
        return orderMapper.toOrderDto(order);
    }

}
