package com.onilicious.EcommerceStartUp.controller;

import com.onilicious.EcommerceStartUp.dto.request.AuthUserRequestDTO;
import com.onilicious.EcommerceStartUp.dto.response.AuthResponseDTO;
import com.onilicious.EcommerceStartUp.dto.response.UserResponseDTO;
import com.onilicious.EcommerceStartUp.entity.User;
import com.onilicious.EcommerceStartUp.mapper.UserMapper;
import com.onilicious.EcommerceStartUp.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody AuthUserRequestDTO request) {
        User savedUser = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toResponse(savedUser));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthUserRequestDTO request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
