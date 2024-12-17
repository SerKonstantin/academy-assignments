package org.academy.spring_mvc_object_mapper.controller;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.academy.spring_mvc_object_mapper.dto.OrderDto;
import org.academy.spring_mvc_object_mapper.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderResource {

    private final OrderService orderService;

    @GetMapping
    public List<OrderDto> getAll() {
        return orderService.getAll();
    }

    @GetMapping("/{id}")
    public OrderDto getOne(@PathVariable UUID id) {
        return orderService.getOne(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public OrderDto create(@RequestBody @Valid OrderDto dto) {
        return orderService.create(dto);
    }

    @PatchMapping("/{id}")
    public OrderDto patch(@PathVariable UUID id, @RequestBody JsonNode patchNode) throws IOException {
        return orderService.patch(id, patchNode);
    }

    @DeleteMapping("/{id}")
    public OrderDto delete(@PathVariable UUID id) {
        return orderService.delete(id);
    }

}
