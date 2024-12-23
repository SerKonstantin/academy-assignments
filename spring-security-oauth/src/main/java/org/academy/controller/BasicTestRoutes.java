package org.academy.controller;

import lombok.RequiredArgsConstructor;
import org.academy.model.Role;
import org.academy.model.User;
import org.academy.repository.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class BasicTestRoutes {

    private final UserRepository userRepository;

    @GetMapping
    public String getHome() {
        return "This page is public. Everyone should see it.";
    }

    // Save my GitHub username in DB as with admin role
    @PostMapping("/promote")
    public String addMyselfInDbAsAdmin() {
        var user = new User();
        user.setUsername("SerKonstantin");
        user.setPassword("password");
        user.setRole(Role.ROLE_ADMIN);
        var savedUser = userRepository.save(user);
        return savedUser + " saved successfully as ADMIN";
    }

    @GetMapping("/user")
    public OAuth2User user(@AuthenticationPrincipal OAuth2User principal) {
        return principal;
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String getAdminPage() {
        return "Private admin page.";
    }

}
