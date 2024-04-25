package com.hacettepe.usermicroservice.repository;

import com.hacettepe.usermicroservice.model.Model;
import com.hacettepe.usermicroservice.model.ShoppingCart;
import com.hacettepe.usermicroservice.model.ShoppingCartModels;
import com.hacettepe.usermicroservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IShoppingCartModelsRepository extends JpaRepository<ShoppingCartModels, Long> {

    void deleteByShoppingCart(ShoppingCart shoppingCart); //empties shopping cart

    @Query(value = "SELECT model_id FROM shopping_cart_models WHERE shopping_cart_id = :cartId", nativeQuery = true)
    List<Long> getModels(long cartId);
}
