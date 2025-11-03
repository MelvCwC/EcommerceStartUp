package com.onilicious.EcommerceStartUp.repository;

import com.onilicious.EcommerceStartUp.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartitemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findByCartId(Long cartId);

    CartItem findByCartIdAndProductId(Long cartId, Long productId);

    void deleteByCartId(Long id);
}
