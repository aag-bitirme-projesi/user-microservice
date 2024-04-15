package com.hacettepe.usermicroservice.repository;

import com.hacettepe.usermicroservice.model.Order;
import com.hacettepe.usermicroservice.model.ShoppingCart;
import com.hacettepe.usermicroservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IOrderRepository extends JpaRepository<Order, String> {
    //@Query(value = "SELECT * FROM orders WHERE _user= :username", nativeQuery = true)
    List<Order> findAllByUser(User user);
}
