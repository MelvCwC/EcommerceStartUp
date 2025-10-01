package com.onilicious.EcommerceStartUp.repository;

import com.onilicious.EcommerceStartUp.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    Cart findByUserId(Long userId);
}
