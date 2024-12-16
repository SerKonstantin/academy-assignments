package org.academy.spring_mvc_with_jsonview.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.academy.spring_mvc_with_jsonview.config.JsonViewRoles;
import org.academy.spring_mvc_with_jsonview.model.User;
import org.academy.spring_mvc_with_jsonview.service.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserResource {

    private final UserServiceImpl userServiceImpl;

    @GetMapping
    @JsonView(JsonViewRoles.UserConcise.class)
    public List<User> getAll() {
        return userServiceImpl.getAll();
    }

    @GetMapping("/{id}")
    @JsonView(JsonViewRoles.UserFull.class)
    public User getOne(@PathVariable UUID id) {
        return userServiceImpl.getOne(id);
    }

    @PostMapping
    @JsonView(JsonViewRoles.UserConcise.class)
    public ResponseEntity<User> create(@RequestBody @Valid User user) {
        return new ResponseEntity<>(userServiceImpl.create(user), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public User patch(@PathVariable UUID id, @RequestBody @Valid JsonNode patchNode) throws IOException {
        return userServiceImpl.patch(id, patchNode);
    }

    @DeleteMapping("/{id}")
    public User delete(@PathVariable UUID id) {
        return userServiceImpl.delete(id);
    }

}
