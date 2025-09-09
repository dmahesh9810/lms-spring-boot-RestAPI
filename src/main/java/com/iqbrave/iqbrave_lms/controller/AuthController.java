package com.iqbrave.iqbrave_lms.controller;

import com.iqbrave.iqbrave_lms.dto.LoginRequest;
import com.iqbrave.iqbrave_lms.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );

            // ✅ Extract user details
            User userDetails = (User) authentication.getPrincipal();
            String role = userDetails.getAuthorities()
                    .iterator()
                    .next()
                    .getAuthority()
                    .replace("ROLE_", ""); // e.g. "ROLE_INSTRUCTOR" → "INSTRUCTOR"

            // ✅ Generate JWT with email + role
            String token = jwtUtil.generateToken(userDetails.getUsername(), role);

            // ✅ Response JSON
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            response.put("role", role);
            response.put("message", "Login successful!");

            return ResponseEntity.ok(response);

        } catch (AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
}
