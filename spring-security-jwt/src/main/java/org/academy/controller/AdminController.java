package org.academy.controller;

import lombok.RequiredArgsConstructor;
import org.academy.model.User;
import org.academy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/unlock-account")
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<String> unlockAccount(@RequestBody String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setAccountNonLocked(true);
        user.setFailedLoginAttempts(0);
        userRepository.save(user);
        return ResponseEntity.ok("User account with username `" + username + "` unlocked successfully.");
    }

}
