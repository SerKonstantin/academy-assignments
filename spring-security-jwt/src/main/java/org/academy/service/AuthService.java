package org.academy.service;

import org.academy.constants.Constants;
import org.academy.dto.AuthRequest;
import org.academy.dto.AuthResponse;
import org.academy.handler.AccountLockedException;
import org.academy.handler.AlreadyExistException;
import org.academy.model.User;
import org.academy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    public User register(AuthRequest authRequest) {
        if (userRepository.existsByUsername(authRequest.getUsername())) {
            throw new AlreadyExistException("Username is occupied, please choose another one");
        }

        var user = new User();
        user.setUsername(authRequest.getUsername());
        user.setPassword(passwordEncoder.encode(authRequest.getPassword()));
        user.setRole(authRequest.getRole());
        User savedUser = userRepository.save(user);
        if (savedUser.getId() > 0) {
            return savedUser;
        } else {
            throw new RuntimeException("User wasn't saved during registration");
        }
    }

    public AuthResponse signIn(AuthRequest authRequest) {
        String username = authRequest.getUsername().toLowerCase().trim();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.isAccountNonLocked()) {
            throw new AccountLockedException("Account is locked due to too many failed login attempts. Please contact support.");
        }

        try {
            var authentication = new UsernamePasswordAuthenticationToken(username, authRequest.getPassword().trim());
            authenticationManager.authenticate(authentication);

            user.setFailedLoginAttempts(0);
            userRepository.save(user);

            var token = jwtUtils.generateToken(user);
            var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);
            return new AuthResponse(token, refreshToken);
        } catch (Exception e) {
            user.setFailedLoginAttempts(user.getFailedLoginAttempts() + 1);

            if (user.getFailedLoginAttempts() >= Constants.MAX_SIGN_IN_FAILS_COUNT) {
                user.setAccountNonLocked(false);
                userRepository.save(user);
                throw new AccountLockedException("Account is locked due to too many failed login attempts. Please contact support.");
            }

            userRepository.save(user);
            return null; // bad design, used for speed
        }
    }

    public AuthResponse refreshToken(String refreshToken) {
        String username = jwtUtils.extractUsername(refreshToken);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (jwtUtils.isTokenValid(refreshToken, user)) {
            String newAccessToken = jwtUtils.generateToken(user);
            String newRefreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);
            return new AuthResponse(newAccessToken, newRefreshToken);
        } else {
            return null; // ugly once again, used for speed
        }
    }

}
