package com.onilicious.EcommerceStartUp.controller;

import com.onilicious.EcommerceStartUp.dto.request.AuthUserRequestDTO;
import com.onilicious.EcommerceStartUp.dto.request.AuthUserUpdateRequestDTO;
import com.onilicious.EcommerceStartUp.dto.response.UserResponseDTO;
import com.onilicious.EcommerceStartUp.entity.User;
import com.onilicious.EcommerceStartUp.mapper.UserMapper;
import com.onilicious.EcommerceStartUp.service.UserService;
import jakarta.validation.Valid;
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
     * Update user
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @Valid @RequestBody AuthUserUpdateRequestDTO request) {
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
