package org.academy.spring_mvc_object_mapper.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Value;

import java.util.UUID;

/**
 * DTO for {@link org.academy.spring_mvc_object_mapper.model.Product}
 */
@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDto {
    @NotBlank(message = "Введите название товара")
    String name;

    String description;

    @Positive(message = "Введите цену товара")
    Integer price;

    @Positive(message = "Введите количество товара на складе")
    Integer amount;

    @NotNull(message = "Укажите в какой заказ входит продукт")
    UUID orderId;
}