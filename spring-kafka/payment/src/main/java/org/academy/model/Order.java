package org.academy.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Order {

    private UUID id;
    private String customer;
    private String product;
    private Integer price;
    private String status;

}