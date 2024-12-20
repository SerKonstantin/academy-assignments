package org.academy;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.academy.dto.AuthRequest;
import org.academy.dto.AuthResponse;
import org.academy.model.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper om;

    /*
    Для скорости запихал весь пайплайн по регистрации и авторизации для доступа к защищенной странице в один метод,
    а весь пайплан по блокировке после неудачных попыток входа и разблокировке админом во второй.
    Тест доступа для роли USER, для остальный ролей аналогично, код копипастить не стал, постманом протыкал.
    */

    @Transactional
    @Test
    void getPublicRoute() throws Exception {
        mockMvc.perform(get("/home")).andExpect(status().isOk());
    }

    @Transactional
    @Test
    void createPlusAuthTest() throws Exception {
        // Without token
        mockMvc.perform(get("/profile")).andExpect(status().isUnauthorized());

        // Register
        var createRequest = new AuthRequest("user1", "pass", UserRole.USER);
        mockMvc.perform(post("/register")
                        .content(om.writeValueAsString(createRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty());

        // Log in
        var loginRequest = new AuthRequest();
        loginRequest.setUsername("user1");
        loginRequest.setPassword("pass");
        var result = mockMvc.perform(post("/login")
                        .content(om.writeValueAsString(loginRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty())
                .andExpect(jsonPath("$.refreshToken").isNotEmpty())
                .andReturn();

        // Parse token from response
        var body = result.getResponse().getContentAsString();
        AuthResponse authResponse = om.readValue(body, AuthResponse.class);
        String token = authResponse.getToken();

        // With token and corresponding role the endpoint is accessible
        mockMvc.perform(get("/profile")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Transactional
    @Test
    void lockAndUnlockAccount() throws Exception {

        // Register user and admin
        var createUserRequest = new AuthRequest("user2", "pass", UserRole.USER);
        var createAdminRequest = new AuthRequest("admin", "admin", UserRole.SUPER_ADMIN);

        mockMvc.perform(post("/register")
                        .content(om.writeValueAsString(createUserRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(post("/register")
                        .content(om.writeValueAsString(createAdminRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // User is being as dumb as a rock
        var userLoginRequest = new AuthRequest();
        userLoginRequest.setUsername("user2");
        userLoginRequest.setPassword("wrong_pass");
        for (var i = 0; i < 5; i++) {
            mockMvc.perform(post("/login")
                    .content(om.writeValueAsString(userLoginRequest))
                    .contentType(MediaType.APPLICATION_JSON));
        }

        // User remembered the pass but it's too late
        userLoginRequest.setPassword("pass");
        mockMvc.perform(post("/login")
                        .content(om.writeValueAsString(userLoginRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$").isString());

        // Admin logs in, parse token from response
        var adminLoginRequest = new AuthRequest();
        adminLoginRequest.setUsername("admin");
        adminLoginRequest.setPassword("admin");
        var result = mockMvc.perform(post("/login")
                .content(om.writeValueAsString(adminLoginRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        var body = result.getResponse().getContentAsString();
        String adminToken = om.readValue(body, AuthResponse.class).getToken();

        // Admin helps
        mockMvc.perform(post("/unlock-account")
                        .header("Authorization", "Bearer " + adminToken)
                        .content("user2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // User now happy and can log in
        mockMvc.perform(post("/login")
                        .content(om.writeValueAsString(userLoginRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty());
    }

}
