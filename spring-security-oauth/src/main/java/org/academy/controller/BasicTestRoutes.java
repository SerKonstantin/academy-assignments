package org.academy.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class BasicTestRoutes {

    @GetMapping
    public String getHome() {
        return "This page is public. Everyone should see it.";
    }

    @GetMapping("/user")
    public String user(@AuthenticationPrincipal OAuth2User principal) {
        return "Private profile. Username from credentials: " + principal.getAttribute("name");
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String getAdminPage() {
        return "Private admin page.";
    }

}
