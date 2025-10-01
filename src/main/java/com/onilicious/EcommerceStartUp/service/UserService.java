package com.onilicious.EcommerceStartUp.service;

import com.onilicious.EcommerceStartUp.entity.User;
import com.onilicious.EcommerceStartUp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    public User createUser(User user) {
        if(userRepo.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email is already taken");
        }
        return userRepo.save(user);
    }

}
