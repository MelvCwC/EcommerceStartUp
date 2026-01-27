package com.onilicious.EcommerceStartUp.service;

import com.onilicious.EcommerceStartUp.dto.request.UserRegisterRequestDTO;
import com.onilicious.EcommerceStartUp.dto.request.UserUpdateRequestDTO;
import com.onilicious.EcommerceStartUp.entity.User;
import com.onilicious.EcommerceStartUp.exception.ConflictException;
import com.onilicious.EcommerceStartUp.exception.ResourceNotFoundException;
import com.onilicious.EcommerceStartUp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepo;

    //Constructor inject dependencies
    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    //Register new user
    //We will be using DTOs if not client can send fields that I do not want like roles, id and risk exposing passwordHash
    public User createUser(UserRegisterRequestDTO request) {
        if(userRepo.existsByEmail(request.getEmail())) {
            throw new ConflictException("Email is already taken");
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        //TODO NOTE: hashing will be added later with spring security
        user.setPasswordHash(request.getPassword());
        user.setRole("USER");
        return userRepo.save(user);
    }

    //Return all users
    public List<User> getAllUser() {
        return userRepo.findAll();
    }

    //Find user by id
    public User getUserById(Long id) {
        return userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    //Update user
    public User updateUser(Long id, UserUpdateRequestDTO request) {
        User existingUser = getUserById(id);

        if(request.getUsername() != null) {
            existingUser.setUsername(request.getUsername());
        }

        if(request.getPassword() != null) {
            existingUser.setPasswordHash(request.getPassword());
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
