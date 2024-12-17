package org.academy.spring_mvc_object_mapper.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.util.UUID;

/**
 * DTO for {@link org.academy.spring_mvc_object_mapper.model.Order}
 */
@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderDto {
    String address;

    @NotBlank(message = "Некорректный статус")
    String status;

    @NotNull(message = "Укажите владельца заказа")
    UUID customerId;
}