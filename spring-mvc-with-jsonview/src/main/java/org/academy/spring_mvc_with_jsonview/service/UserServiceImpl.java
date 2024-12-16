package org.academy.spring_mvc_with_jsonview.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.academy.spring_mvc_with_jsonview.model.User;
import org.academy.spring_mvc_with_jsonview.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserServiceImpl {

    private final UserRepository userRepository;

    private final ObjectMapper objectMapper;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getOne(UUID id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));
    }

    public User create(User user) {
        return userRepository.save(user);
    }

    public User patch(UUID id, JsonNode patchNode) throws IOException {
        User user = userRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));

        objectMapper.readerForUpdating(user).readValue(patchNode);

        return userRepository.save(user);
    }

    public User delete(UUID id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            userRepository.delete(user);
        }
        return user;
    }

}
