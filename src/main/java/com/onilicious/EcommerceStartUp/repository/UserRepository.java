package com.onilicious.EcommerceStartUp.repository;

import com.onilicious.EcommerceStartUp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    Optional<User> findByUsername(String username);

    boolean existsByEmail(String email);
}
