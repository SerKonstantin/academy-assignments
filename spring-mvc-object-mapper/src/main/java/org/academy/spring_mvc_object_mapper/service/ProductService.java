package org.academy.spring_mvc_object_mapper.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.academy.spring_mvc_object_mapper.dto.ProductDto;
import org.academy.spring_mvc_object_mapper.mapper.ProductMapper;
import org.academy.spring_mvc_object_mapper.model.Order;
import org.academy.spring_mvc_object_mapper.model.Product;
import org.academy.spring_mvc_object_mapper.repository.OrderRepository;
import org.academy.spring_mvc_object_mapper.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ProductService{

    private final ProductMapper productMapper;

    private final ProductRepository productRepository;

    private final ObjectMapper objectMapper;

    private final OrderRepository orderRepository;

    public List<ProductDto> getAll() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(productMapper::toProductDto)
                .toList();
    }

    public ProductDto getOne(UUID id) {
        Optional<Product> productOptional = productRepository.findById(id);
        return productMapper.toProductDto(productOptional.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id))));
    }

    public ProductDto create(ProductDto dto) {
        Order order = orderRepository.findById(dto.getOrderId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));

        Product product = productMapper.toEntity(dto);
        product.setOrder(order);

        Product resultProduct = productRepository.save(product);
        return productMapper.toProductDto(resultProduct);
    }

    public ProductDto patch(UUID id, JsonNode patchNode) throws IOException {
        Product product = productRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));

        ProductDto productDto = productMapper.toProductDto(product);
        objectMapper.readerForUpdating(productDto).readValue(patchNode);

        if (productDto.getOrderId() != null) {
            Order order = orderRepository.findById(productDto.getOrderId()).orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
            product.setOrder(order);
        }

        productMapper.updateWithNull(productDto, product);
        Product resultProduct = productRepository.save(product);
        return productMapper.toProductDto(resultProduct);
    }

    public ProductDto delete(UUID id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product != null) {
            productRepository.delete(product);
        }
        return productMapper.toProductDto(product);
    }
}
