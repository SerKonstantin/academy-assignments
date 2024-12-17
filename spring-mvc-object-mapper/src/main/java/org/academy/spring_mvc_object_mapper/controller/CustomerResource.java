package org.academy.spring_mvc_object_mapper.controller;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.academy.spring_mvc_object_mapper.dto.CustomerDto;
import org.academy.spring_mvc_object_mapper.service.CustomerService;
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
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerResource {

    private final CustomerService customerService;

    @GetMapping
    public List<CustomerDto> getAll() {
        return customerService.getAll();
    }

    @GetMapping("/{id}")
    public CustomerDto getOne(@PathVariable UUID id) {
        return customerService.getOne(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CustomerDto create(@RequestBody @Valid CustomerDto dto) {
        return customerService.create(dto);
    }

    @PatchMapping("/{id}")
    public CustomerDto patch(@PathVariable UUID id, @RequestBody JsonNode patchNode) throws IOException {
        return customerService.patch(id, patchNode);
    }

    @DeleteMapping("/{id}")
    public CustomerDto delete(@PathVariable UUID id) {
        return customerService.delete(id);
    }

}
