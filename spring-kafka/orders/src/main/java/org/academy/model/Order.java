package org.academy.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    private UUID id;

    @NotBlank
    private String customer;

    @NotBlank
    private String product;

    @NotNull
    private Integer price;

    private String status;
}