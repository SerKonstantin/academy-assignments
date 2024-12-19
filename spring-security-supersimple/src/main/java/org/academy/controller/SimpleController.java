package org.academy.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping
public class SimpleController {

    @GetMapping
    public ResponseEntity<Void> redirect() {
        var headers = new HttpHeaders();
        headers.setLocation(URI.create("/home"));
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

    @GetMapping("/home")
    public String getHome() {
        return "This page is public";
    }

    @GetMapping("/users")
    public String getUsers() {
        return "This page is accessible only to authorized users";
    }

}
