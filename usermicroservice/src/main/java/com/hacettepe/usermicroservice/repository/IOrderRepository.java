package com.hacettepe.usermicroservice.repository;

import com.hacettepe.usermicroservice.model.Order;
import com.hacettepe.usermicroservice.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IOrderRepository extends JpaRepository<Order, String> {
    @Query(value = "SELECT * FROM orders WHERE user.username = :username", nativeQuery = true)
    public List<Order> findByUser(String username);
}
