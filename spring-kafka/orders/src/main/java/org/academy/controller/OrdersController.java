package org.academy.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class OrdersController {

    @GetMapping
    public String getPageStub() {
        return "Orders page stub";
    }

}
