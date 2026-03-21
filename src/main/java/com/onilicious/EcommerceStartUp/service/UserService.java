package com.onilicious.EcommerceStartUp.service;

import com.onilicious.EcommerceStartUp.dto.request.AuthUserUpdateRequestDTO;
import com.onilicious.EcommerceStartUp.entity.User;
import com.onilicious.EcommerceStartUp.exception.ResourceNotFoundException;
import com.onilicious.EcommerceStartUp.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    //Constructor inject dependencies
    public UserService(UserRepository userRepo, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JWTService jwtService) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    //Return all users
    public List<User> getAllUser() {
        return userRepo.findAll();
    }

    //Find user by id
    public User getUserById(Long id) {
        return userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    /*
     * Update user
     */
    public User updateUser(Long id, AuthUserUpdateRequestDTO request) {
        User existingUser = getUserById(id);

        if(request.getUsername() != null) {
            existingUser.setUsername(request.getUsername());
        }

        if(request.getPassword() != null) {
            existingUser.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        return userRepo.save(existingUser);
    }

    //Delete user
    public void deleteUser(Long id) {
        if(!userRepo.existsById(id)) {
            throw new ResourceNotFoundException("User not found");
        }

        userRepo.deleteById(id);
    }

}
