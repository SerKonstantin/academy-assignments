package org.academy.spring_mvc_object_mapper.controller;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.academy.spring_mvc_object_mapper.dto.ProductDto;
import org.academy.spring_mvc_object_mapper.service.ProductService;
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
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductResource {

    private final ProductService productService;

    @GetMapping
    public List<ProductDto> getAll() {
        return productService.getAll();
    }

    @GetMapping("/{id}")
    public ProductDto getOne(@PathVariable UUID id) {
        return productService.getOne(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ProductDto create(@RequestBody @Valid ProductDto dto) {
        return productService.create(dto);
    }

    @PatchMapping("/{id}")
    public ProductDto patch(@PathVariable UUID id, @RequestBody JsonNode patchNode) throws IOException {
        return productService.patch(id, patchNode);
    }

    @DeleteMapping("/{id}")
    public ProductDto delete(@PathVariable UUID id) {
        return productService.delete(id);
    }

}
