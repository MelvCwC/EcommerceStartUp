package com.onilicious.EcommerceStartUp.service;

import com.onilicious.EcommerceStartUp.entity.User;
import com.onilicious.EcommerceStartUp.exception.ResourceNotFoundException;
import com.onilicious.EcommerceStartUp.model.UserPrincipal;
import com.onilicious.EcommerceStartUp.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepo;

    public CustomUserDetailsService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return new UserPrincipal(user);
    }
}
