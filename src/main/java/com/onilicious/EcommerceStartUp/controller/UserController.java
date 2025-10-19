package com.onilicious.EcommerceStartUp.controller;

import com.onilicious.EcommerceStartUp.entity.User;
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
    @RequestMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUser();
    }

    /*
     * Get individual user by id
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    /*
     * Create user
     */
    @PostMapping("/register")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        //@RequestBody will take JSON data from HTTP request body and convert it into a Java object and give it to the method
        User newUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    /*
     * Update user
     */
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        User user = userService.updateUser(id, updatedUser);
        return ResponseEntity.ok(user);
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
