package com.onilicious.EcommerceStartUp.service;

import com.onilicious.EcommerceStartUp.dto.request.AuthUserRequestDTO;
import com.onilicious.EcommerceStartUp.dto.response.AuthResponseDTO;
import com.onilicious.EcommerceStartUp.entity.Role;
import com.onilicious.EcommerceStartUp.entity.User;
import com.onilicious.EcommerceStartUp.exception.ConflictException;
import com.onilicious.EcommerceStartUp.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserService userService;
    private final JWTService jwtService;
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserService userService, UserRepository userRepo, PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager, JWTService jwtService) {
        this.userService = userService;
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    /*
     * Register user
     * We will be using DTOs if not client can send fields that I do not want like roles, id and risk exposing passwordHash
     */
    public User register(AuthUserRequestDTO request) {
        if(userRepo.findByUsername(request.getUsername()).isPresent()) {
            throw new ConflictException("Username already taken");
        }
        User user = new User();
        user.setUsername(request.getUsername());
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPassword(request.getPassword());
        user.setRole(Role.USER);
        return userRepo.save(user);
    }

    /*
     * Login
     */
    public AuthResponseDTO login(AuthUserRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        String token = jwtService.generateToken(request.getUsername());

        return new AuthResponseDTO(token);
    }
}
