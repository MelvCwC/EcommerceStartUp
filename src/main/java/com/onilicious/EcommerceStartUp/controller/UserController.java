package com.onilicious.EcommerceStartUp.controller;

import com.onilicious.EcommerceStartUp.dto.request.UserRegisterRequestDTO;
import com.onilicious.EcommerceStartUp.dto.request.UserUpdateRequestDTO;
import com.onilicious.EcommerceStartUp.dto.response.UserResponseDTO;
import com.onilicious.EcommerceStartUp.entity.User;
import com.onilicious.EcommerceStartUp.mapper.UserMapper;
import com.onilicious.EcommerceStartUp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /*
     * Get all users
     */
    @GetMapping
    public List<UserResponseDTO> getAllUsers() {
        return userService.getAllUser().stream().map(UserMapper::toResponse).toList();
    }

    /*
     * Get individual user by id
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(UserMapper.toResponse(userService.getUserById(id)));
    }

    /*
     * Create user
     */
    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRegisterRequestDTO request) {
        //@RequestBody will take JSON data from HTTP request body and convert it into a Java object and give it to the method
        User savedUser = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toResponse(savedUser));
    }

    /*
     * Update user
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @RequestBody UserUpdateRequestDTO request) {
        User updatedUser = userService.updateUser(id, request);
        return ResponseEntity.ok(UserMapper.toResponse(updatedUser));
    }

    /*
     * Delete user
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }

}
