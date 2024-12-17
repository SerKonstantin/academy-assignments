package org.academy.spring_mvc_object_mapper.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Value;

/**
 * DTO for {@link org.academy.spring_mvc_object_mapper.model.Customer}
 */
@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerDto {
    @NotBlank(message = "Введите имя пользователя")
    String name;

    @Email(message = "Некорретный формат электронной почты")
    String email;

    @Pattern(message = "Ведите номер телефона в формате +7 981 123 45 67", regexp = "^\\+(\\d+([ -]\\d+)*)$")
    String phoneNumber;
}