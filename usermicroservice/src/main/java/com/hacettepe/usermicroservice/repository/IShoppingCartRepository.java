package com.hacettepe.usermicroservice.repository;

import com.hacettepe.usermicroservice.model.Model;
import com.hacettepe.usermicroservice.model.ShoppingCart;
import com.hacettepe.usermicroservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IShoppingCartRepository extends JpaRepository<ShoppingCart, String> {
    @Query(value = "SELECT * FROM shopping_cart WHERE user = :username", nativeQuery = true)
    Optional<ShoppingCart> findByUser(User username);

    @Query(value = "UPDATE shopping_cart SET models = NULL WHERE user = :username", nativeQuery = true)
    void emptyShoppingCart(User username);

    @Query(value = "UPDATE shopping_cart SET models = :newModels WHERE user = :username", nativeQuery = true)
    void updateShoppingCart(User username, List<Model> newModels);
}
