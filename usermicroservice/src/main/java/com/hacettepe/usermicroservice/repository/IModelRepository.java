package com.hacettepe.usermicroservice.repository;

import com.hacettepe.usermicroservice.model.Model;
import com.hacettepe.usermicroservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface IModelRepository extends JpaRepository<Model, Long> {

    Optional<Model> findByName(String name);

    Optional<Model> findById(long id);

    @Query(value = "SELECT m.* FROM models m INNER JOIN shopping_cart_models scm ON m.id = scm.model_id WHERE scm.shopping_cart_id = :cartId", nativeQuery = true)
    List<Model> findModelsByCartId(@Param("cartId") long cartId);

    @Query(value = "SELECT m.* FROM models m INNER JOIN orders o WHERE o.model_id = m.id and o._user = :user", nativeQuery = true)
    List<Model> findBoughtModels(User user);

    void deleteById(long id);
}
