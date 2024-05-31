package com.hacettepe.usermicroservice.repository;

import com.hacettepe.usermicroservice.model.DevelopersModel;
import com.hacettepe.usermicroservice.model.Model;
import com.hacettepe.usermicroservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IDevelopersModelRepository extends JpaRepository<DevelopersModel, Long> {

    @Query(value = "SELECT model FROM developers_models WHERE user = :username", nativeQuery = true)
    List<Model> findByUser(String username);

    void deleteByUser(User user);
}
