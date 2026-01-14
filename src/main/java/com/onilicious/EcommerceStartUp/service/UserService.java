package com.onilicious.EcommerceStartUp.service;

import com.onilicious.EcommerceStartUp.entity.User;
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
    public User createUser(User user) {
        if(userRepo.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email is already taken");
        }
        return userRepo.save(user);
    }

    //Return all users
    public List<User> getAllUser() {
        return userRepo.findAll();
    }

    //Find user by id
    public User getUserById(Long id) {
        return userRepo.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    //Update user
    public User updateUser(Long id, User updatedUser) {
        User existingUser = getUserById(id);
        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setPasswordHash(updatedUser.getPasswordHash());
        existingUser.setRole(updatedUser.getRole());
        return userRepo.save(existingUser);
    }

    //Delete user
    public void deleteUser(Long id) {
        if(!userRepo.existsById(id)) {
            throw new RuntimeException("User not found");
        }

        userRepo.deleteById(id);
    }

}
