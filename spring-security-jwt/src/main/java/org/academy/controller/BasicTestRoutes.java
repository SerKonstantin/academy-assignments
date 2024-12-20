package org.academy.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class BasicTestRoutes {

    @GetMapping("/home")
    public String getHome() {
        return "Public page. Shown to anyone";
    }

    @GetMapping("/profile")
    public String getProfile() {
        return "Personal profile page. Only shown to authorized users";
    }


    @GetMapping("/moderator")
    @PreAuthorize("hasAuthority('MODERATOR')")
    public String getModeratorPage() {
        return "Page should be accessible only to moderators";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    public String getAdminPage() {
        return "Page should be accessible only to admins";
    }

}
