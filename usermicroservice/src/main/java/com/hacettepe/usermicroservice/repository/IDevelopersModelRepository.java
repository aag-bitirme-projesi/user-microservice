package com.hacettepe.usermicroservice.repository;

import com.hacettepe.usermicroservice.model.DevelopersModel;
import com.hacettepe.usermicroservice.model.Model;
import com.hacettepe.usermicroservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IDevelopersModelRepository extends JpaRepository<DevelopersModel, Long> {

    Optional<DevelopersModel> findByUserAndModel(User user, Model model);
}
