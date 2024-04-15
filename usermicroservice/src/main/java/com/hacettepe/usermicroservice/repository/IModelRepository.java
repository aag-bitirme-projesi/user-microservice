package com.hacettepe.usermicroservice.repository;

import com.hacettepe.usermicroservice.model.Model;
import com.hacettepe.usermicroservice.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IModelRepository extends JpaRepository<Model, String> {

    @Query(value = "SELECT m.* FROM models m INNER JOIN shopping_cart_models scm ON m.id = scm.model_id WHERE scm.shopping_cart_id = :cartId", nativeQuery = true)
    List<Model> findModelsByCartId(@Param("cartId") long cartId);
}
