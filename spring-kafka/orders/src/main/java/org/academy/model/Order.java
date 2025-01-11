package org.academy.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
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