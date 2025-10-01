package com.onilicious.EcommerceStartUp.repository;

import com.onilicious.EcommerceStartUp.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUserId(Long userId);

    List<Order> findByStatus(String status);
}
