package com.hacettepe.usermicroservice.repository;

import com.hacettepe.usermicroservice.model.Model;
import com.hacettepe.usermicroservice.model.ShoppingCart;
import com.hacettepe.usermicroservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IShoppingCartRepository extends JpaRepository<ShoppingCart, String> {
//    @Query(value = "SELECT * FROM shopping_cart WHERE user = :username", nativeQuery = true)
//    ShoppingCart findByUser(@Param("username") String username);
    ShoppingCart findByUser(User user);
}
